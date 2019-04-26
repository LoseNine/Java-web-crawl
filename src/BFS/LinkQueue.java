package BFS;

import java.util.HashSet;
import java.util.Set;

public class LinkQueue {
    //被访问的URL集合
    private static Set visitedUrl=new HashSet();
    //待访问的URL
    private static Queue unVisitedUrl=new Queue();
    //获取URL队列
    public static Queue getUnVisitedUrl(){
        return unVisitedUrl;
    }
    //添加到访问过的URL队列中
    public static void addVisitedUrl(String url){
        visitedUrl.add(url);
    }
    //移除访问过的URL
    public static void removeVisitedUrl(String url){
        visitedUrl.remove(url);
    }
    //未访问的URL出队列
    public static Object unVisitedUrlDeQueue(){
        return unVisitedUrl.deQueu();
    }
    //保证每个URL被访问一次
    public static void addUnvisitedUrl(String url){
        if(url!=null && !url.trim().equals("") &&
                !visitedUrl.contains(url) && !unVisitedUrl.contains(url)){
            unVisitedUrl.enQueue(url);
        }
    }
    //获取已访问的URL数目
    public static int getVisitedUrlNum(){
        return visitedUrl.size();
    }
    //判断未访问的URL队列是否为空
    public static boolean unVisistedUrlEmpty(){
        return unVisitedUrl.empty();
    }
}
