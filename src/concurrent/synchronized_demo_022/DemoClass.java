package concurrent.synchronized_demo_022;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * 线程队列
 * 卖票问题
 * 加锁方法:效率太低，只能一个线程操作
 *
 */
public class DemoClass {
    static List<String> tickets = new LinkedList();

    static {
        for (int i = 1; i <= 100; i++) {
            tickets.add("编号" + i);
        }
    }

    public static void main(String[] args) {
        Object o = new Object();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (tickets.size() > 0) {
                    synchronized (o) {
                        if (tickets.size() <= 0) {
                            break;
                        }
                        try {
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("销售了--" + tickets.remove(0));
                    }
                }
            }).start();
        }
    }
}
