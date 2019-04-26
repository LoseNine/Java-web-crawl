package BFS;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

public class DownloadFile {
    /**
     * 根据URL和网页类型生成需要保存的网页的文件名，取出URL的非法字符
     */
    public String getFileNameByUrl(String url,String contentType){
        //移除HTTP
        url=url.substring(7);
        if(contentType.indexOf("html")!=-1){
            url=url.replaceAll("[\\?/:.*|<>\"]","_")+".html";
            return url;
        }
        else {
            return url.replaceAll("[\\?/:.*|<>\"]","_")
                    +"."+contentType.substring(contentType.lastIndexOf("/")+1);
        }
    }
    /**
     * 保存网页字节数组到本地文件，filepath为要保存的文件地址
     */
    private void saveToLocal(HttpEntity data,String filepath){
        try{
            InputStream inputStream=data.getContent();
            OutputStream outputStream=new FileOutputStream(filepath);
            int tmp=-1;
            while((tmp=inputStream.read())>0){
                outputStream.write(tmp);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 下载URL指向的网页
     */
    public String downloadFile(String url){
        String filepath=null;
        CloseableHttpClient httpClient= HttpClients.createDefault();
        RequestConfig requestConfig=RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(5000)
                .build();
        HttpGet httpGet=new HttpGet(url);
        httpGet.setConfig(requestConfig);
        try{
            System.out.println("downing..."+url);
            CloseableHttpResponse response=httpClient.execute(httpGet);

            if(response.getStatusLine().getStatusCode()!= HttpStatus.SC_OK){
                System.err.println("Method failed :"+response.getStatusLine());
                filepath=null;
            }

            //处理HTTP响应
            HttpEntity responseBody=response.getEntity();
            //根据网页URL生成保存的filename
            filepath="D:\\JAVA1\\Crawl\\src\\BFS\\temp\\"+getFileNameByUrl(url,responseBody.getContentType().getValue());
            saveToLocal(responseBody,filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filepath;
    }
}

