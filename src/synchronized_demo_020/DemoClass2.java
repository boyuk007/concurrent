package synchronized_demo_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * ReentrantLock的其他方法
 */
public class DemoClass2 {
    Lock lock = new ReentrantLock();

    void m1() {
        lock.lock();
        try {
            System.out.println("m1开始.............");
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /*
      lockInterruptibly(),可以对线程interrupt方法做出响应
      在一个线程等待锁的过程中，可以被打断
     */
    void m2() {
        try {
//            lock.lock();
            lock.lockInterruptibly();
            System.out.println("m2开始.............");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("m2 end.............");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("m2被打断");
        } finally {
            if (lock.tryLock()) {
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) {
        DemoClass2 d = new DemoClass2();
        new Thread(() -> d.m1()).start();
        Thread t2 = new Thread(() -> d.m2());
        t2.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();
    }

}



