package BFS;

import java.util.LinkedList;
import java.util.List;

public class Queue {
    //链表实现队列
    private LinkedList queue=new LinkedList();

    //入队列
    public void enQueue(Object obj){
        queue.addLast(obj);
    }
    //出队列
    public Object deQueu(){
        return queue.removeFirst();
    }

    public boolean isQueueEmpty(){
        return queue.isEmpty();
    }
    public boolean contains(Object obj){
        return queue.contains(obj);
    }
    public boolean empty(){
        return queue.isEmpty();
    }
}
