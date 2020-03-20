package concurrent.synchronized_demo_023;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
 * Queue
 * ConcurrentLinkedQueue（单向队列）:链表形式的队列；无界队列，可以一直往里加
 * Deque（双向队列）
 *
 */
public class DemoClass3 {
    public static void main(String[] args) {
        Queue<String> clq = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 10; i++) {
            clq.offer("a" + i);
        }
        System.out.println(clq);
        System.out.println(clq.size());
        //poll取出最后一个元素并删除
        System.out.println(clq.poll());
        System.out.println(clq.size());
        //peek取出最后一个元素
        System.out.println(clq.peek());
        System.out.println(clq.size());
    }
}
