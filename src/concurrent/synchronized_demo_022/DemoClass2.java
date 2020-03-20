package concurrent.synchronized_demo_022;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
 * 线程队列
 * 卖票问题
 * 队列方法
 *
 */
public class DemoClass2 {
    static Queue<String> tickets = new ConcurrentLinkedQueue();

    static {
        for (int i = 1; i <= 100; i++) {
            tickets.add("编号" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    String poll = tickets.poll();
                    if (poll == null) {
                        break;
                    } else {
                        System.out.println("销售了--" + poll);
                    }
                }
            }).start();
        }
    }
}
