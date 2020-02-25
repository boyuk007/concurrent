package synchronized_demo_023;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/*
 * Queue
 * SynchronousQueue:一种特殊的TransferQueue，容量为0；
 *
 */
public class DemoClass8_SynchronousQueue {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> ltq = new SynchronousQueue();
        new Thread(() -> {
            try {
                System.out.println(ltq.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        ltq.put("aaa");//阻塞等待消费者消费（调用的是transfer方法）
//        ltq.add("aaa");//无法使用该方法，因为容量为0
        System.out.println(ltq.size());
    }
}
