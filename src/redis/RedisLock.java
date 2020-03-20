package redis;

import java.util.concurrent.locks.ReentrantLock;

/*
 * redis分布式锁
 */
public class RedisLock implements Runnable{
        public static ReentrantLock lock1 = new ReentrantLock();
        int lock;
        /**
         * 控制加锁顺序，产生死锁
         */
        public RedisLock(int lock) {
            this.lock = lock;
        }
        public void run() {
            try {

                lock1.lock(); // 如果当前线程未被 中断，则获取锁。
                try {
                    System.out.println(Thread.currentThread().getName()+"，进入锁！");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"，执行完毕！");
            } finally {
                // 查询当前线程是否保持此锁。
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
                System.out.println(Thread.currentThread().getName() + "，退出。");
            }
        }
        public static void main(String[] args) throws InterruptedException {
            RedisLock intLock1 = new RedisLock(1);
            RedisLock intLock2 = new RedisLock(2);
            Thread thread1 = new Thread(intLock1, "线程1");
            Thread thread2 = new Thread(intLock2, "线程2");
            thread1.start();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread2.start();
            Thread.sleep(1000);
            thread1.interrupt(); // 中断线程2
        }

}
