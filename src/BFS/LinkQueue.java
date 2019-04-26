package BFS;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Queue;

public class LinkQueue {
    //被访问的URL集合
    private static Set visitedUrl=new HashSet();
    //待访问的URL
    //private static Queue unVisitedUrl=new Queue();

    //更改为优先级队列，实现带偏好的爬虫
    private static Queue unVisitedUrl= new PriorityQueue();

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
        //return unVisitedUrl.deQueu();
        return unVisitedUrl.poll();
    }
    //保证每个URL被访问一次
    public static void addUnvisitedUrl(String url){
        if(url!=null && !url.trim().equals("") &&
                !visitedUrl.contains(url) && !unVisitedUrl.contains(url)){
            //unVisitedUrl.enQueue(url);
            unVisitedUrl.add(url);
        }
    }
    //获取已访问的URL数目
    public static int getVisitedUrlNum(){
        return visitedUrl.size();
    }
    //判断未访问的URL队列是否为空
    public static boolean unVisistedUrlEmpty(){
        //return unVisitedUrl.empty();
        return unVisitedUrl.isEmpty();
    }
}
