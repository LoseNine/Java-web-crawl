package BFS;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class HtmlParseTool {
    //获取一个网站上的连接，filter用来过滤
    public static Set<String> extractLinks(String url,LinkFilter filter){
        Set<String> links=new HashSet<>();
        try{
            Document document= Jsoup.connect(url)
                    .header("Connection", "close")//关闭Keep-Alive
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Referer", "https://www.baidu.com/")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(5000)
                    .get();
            Elements hlinks = document.select("a[href]");
            Elements media = document.select("[src]");
            Elements imports = document.select("link[href]");

            print("\nMedia: (%d)", media.size());
            for (Element src : media) {
                if (src.tagName().equals("img")){
                    if (filter.accept(src.attr("sabs:rc"))){
                        links.add(src.attr("abs:src"));
                        print(" * %s: <%s> %sx%s (%s)",
                                src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                                trim(src.attr("alt"), 20));
                    }
                }
            }

            print("\nImports: (%d)", imports.size());
            for (Element link : imports) {
                if (links.add(link.attr("abs:href"))
                        && filter.accept(link.attr("abs:href"))){
                    links.add(link.attr("abs:href"));
                    print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));

                }

            }

            print("\nLinks: (%d)", hlinks.size());
            for (Element link : hlinks) {
                if (links.add(link.attr("abs:href"))
                        && filter.accept(link.attr("abs:href"))){
                    links.add(link.attr("abs:href"));
                    print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return links;
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
}
