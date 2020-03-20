package concurrent.synchronized_demo_023;

import java.util.concurrent.LinkedTransferQueue;

/*
 * Queue
 * LinkedTransferQueue
 * 特殊方法--transfer()：当前有消费者的情况下直接将数据给消费者，不插入队列；但是没有消费者的情况下，将指定的元素插入到该队列的尾部，会阻塞,等待消费者消费该元素
 *
 */
public class DemoClass7_LinkedTransferQueue {

    public static void main(String[] args) throws InterruptedException {
        LinkedTransferQueue<String> ltq = new LinkedTransferQueue();
        new Thread(() -> {
            try {
                System.out.println(ltq);
                System.out.println(ltq.take());
//                System.out.println(ltq.poll(1,TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println(ltq);
        ltq.transfer("aaa");
        System.out.println(ltq);
    }
}
