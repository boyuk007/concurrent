package concurrent.synchronized_demo_020;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * ReentrantLock的其他方法
 * ReentrantLock可以指定为公平锁
 * 公平锁：更待时间长的线程先拿到锁；但较消耗性能，因为要记录每个线程的等待时间
 */
public class DemoClass3 implements Runnable {
    private static Lock lock = new ReentrantLock(true);

    public void run() {
        for (int i = 0; i < 100; i++) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "get");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        DemoClass3 d = new DemoClass3();
        new Thread(d).start();
        new Thread(d).start();
    }
}



