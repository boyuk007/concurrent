package concurrent.synchronized_demo_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * ReentrantReadWriteLock:读写锁
 * 允许锁降级，不允许锁升级
 * 写锁独占，读锁共享
 */
public class DemoClass4 {
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void read() throws InterruptedException {
        lock.readLock().lock();
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "正在读取....");
                TimeUnit.SECONDS.sleep(1);
//                lock.writeLock().lock(); //锁升级，会造成死锁
//                TimeUnit.SECONDS.sleep(1);
//                lock.writeLock().unlock();
            }
        } finally {
            System.out.println(Thread.currentThread().getName() + "读取结束");
            lock.readLock().unlock();
        }
    }

    public static void write() throws InterruptedException {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在写入....");
            TimeUnit.SECONDS.sleep(2);
            lock.readLock().lock();//锁降级
            System.out.println(Thread.currentThread().getName() + "插入读锁");
            lock.readLock().unlock();
        } finally {
            System.out.println(Thread.currentThread().getName() + "写入结束");
            lock.writeLock().unlock();
        }
    }

    public static void main(String[] args){
        new Thread(()->{
            try {
                write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}



