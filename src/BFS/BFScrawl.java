package BFS;

import java.util.Set;

public class BFScrawl {
    /**
     * 广度优先爬虫
     * 使用种子初始化URL队列
     * @return
     */
    private void initCrawlerWithSeeds(String[] seeds){
        for (String i:seeds){
            LinkQueue.addUnvisitedUrl(i);
        }
    }
    //抓取过程
    public void crawling(String[] seeds){
        //定义过滤器，提取
        LinkFilter filter=new LinkFilter() {
            @Override
            public boolean accept(String url) {
                if (url.startsWith("http")){
                    return true;
                }else {
                    return false;
                }
            }
        };
        //初始化队列
        initCrawlerWithSeeds(seeds);
        //循环条件：待抓取的连接不空且网页不多于1000
        while (!LinkQueue.unVisistedUrlEmpty()&&
        LinkQueue.getVisitedUrlNum()<=10){
            String visitURl=(String)LinkQueue.unVisitedUrlDeQueue();
            if(visitURl==null){
                continue;
            }
            DownloadFile downloadFile=new DownloadFile();
            //下载网页
            downloadFile.downloadFile(visitURl);
            //url放入已访问中
            LinkQueue.addVisitedUrl(visitURl);
            //提取下载网页的URL
            Set<String> links=HtmlParseTool.extractLinks(visitURl,filter);
            //新访问的URL出队列
            for(String link:links){
                LinkQueue.addUnvisitedUrl(link);
            }
        }
    }
    public static void main(String  [] args){
        BFScrawl scrawl=new BFScrawl();
        scrawl.crawling(new String[]{"http://www.baidu.com"});
    }
}
