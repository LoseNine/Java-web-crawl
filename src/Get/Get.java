package Get;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.protocol.HttpContext;

import java.io.*;
import java.net.URISyntaxException;
/**
 * httpclient-4.5所需jar包，
 * 里面包含httpclient-4.5.jar等
 * commons-codec-1.9.jar
 * commons-logging-1.2.jar
 * fluent-hc-4.5.2.jar
 * httpclient-4.5.2.jar
 * httpclient-cache-4.5.2.jar
 * httpcore-4.4.2.jar
 * httpmime-4.5.2.jar
 */
public class Get {
    public static void main(String[] args) throws IOException, URISyntaxException {
        example_get();
    }

    public static void example_get() throws URISyntaxException, IOException {
        //创建httpclient实例，采用默认的参数配置
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String uri="http://www.baidu.com";
        //通过URIBuilder类创建URI效果一样
//        URI uri =new URIBuilder()
//                .setScheme("http")
//                .setHost("www.baidu.com")
//                .setPath("/search")
//                .setParameter("q", "httpclient")
//                .build();

        HttpGet get = new HttpGet(uri) ;   //使用Get方法提交
        //请求的参数配置，分别设置连接池获取连接的超时时间，连接上服务器的时间，服务器返回数据的时间
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                //.setProxy(new HttpHost("127.0.0.1",8080))
                .build();
        //配置信息添加到Get请求中
        get.setConfig(config);
        //通过httpclient的execute提交 请求 ，并用CloseableHttpResponse接受返回信息
        CloseableHttpResponse response = httpClient.execute(get);
        //服务器返回的状态
        int statusCode = response.getStatusLine().getStatusCode() ;
        HttpEntity httpEntity=response.getEntity();

        if((statusCode==HttpStatus.SC_MOVED_PERMANENTLY)||(statusCode==HttpStatus.SC_MOVED_TEMPORARILY)){
            Header header=response.getFirstHeader("location");
            if (header!=null){
                String newUrl=header.getValue();
                if(newUrl==null||newUrl.equals("")){
                    newUrl="/";
                    //do somethings
                }
            }
        }

        if(statusCode == HttpStatus.SC_OK){
            //EntityUtils 获取返回的信息。官方不建议使用使用此类来处理信息
            //原始实体的内容被读入内存缓冲区。在其他方面与原始实体相同。
            HttpEntity entity=new BufferedHttpEntity(httpEntity);
           // System.out.println("html:\n" + EntityUtils.toString(entity , "UTF-8"));
            InputStream inputStream=entity.getContent();

            OutputStream outputStream=new FileOutputStream("./baidu.tml");
            int temp=-1;
            while((temp=inputStream.read())>0){
                outputStream.write(temp);
            }
            if(inputStream!=null){
                inputStream.close();
            }
            if(outputStream!=null){
                outputStream.close();
            }
        }else {
            System.out.println("html:\n" + "获取信息失败");
        }
        response.close();
    }
}
