package concurrent.synchronized_demo_019;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 多生产者多消费者问题
 * 使用Lock，提高效率
 */
public class DemoClass {

    public static void main(String[] args) {
        Resource r = new Resource();
        Producer producer = new Producer(r);
        Consumer consumer = new Consumer(r);
        new Thread(producer).start();
        new Thread(producer).start();
        new Thread(consumer).start();
        new Thread(consumer).start();
    }

}

class Resource {
    private String name;
    private int count = 1;
    private boolean flag = false;
    //创建一个锁对象
    Lock lock = new ReentrantLock();
    //获取该锁上的监视器对象
    Condition producer_con = lock.newCondition();
    Condition consumer_con = lock.newCondition();

    public void set(String name) {
        lock.lock();
        try {
            while (flag) {//使用if，被唤醒后悔直接往下执行，不会再进行判断，造成线程安全问题；使用while强制每次唤醒都进行判断
                try {
//                    lock.wait();
                    producer_con.await();//能够对指定的监视器进行操作
                } catch (InterruptedException e) {

                }
            }
            this.name = name + count;
            count++;
            try { TimeUnit.MILLISECONDS.sleep(10); } catch (InterruptedException e) { }
            System.out.println(Thread.currentThread().getName() + "...生产者..." + this.name);
            flag = true;
//            notifyAll();//使用notify，有可能唤醒的是生产者，会造成死锁
            consumer_con.signal();//指定唤醒消费者的线程，弥补了Object类需要使用notifyAll()唤醒所有的线程的缺陷，节省了性能
        } finally {
            lock.unlock();
        }
    }

    public void out(String name) {
        lock.lock();
        try {
            while (!flag) {
                try {
//                    lock.wait();
                    consumer_con.await();
                } catch (InterruptedException e) {

                }
            }
            try { TimeUnit.MILLISECONDS.sleep(10); } catch (InterruptedException e) { }
            System.out.println(Thread.currentThread().getName() + "...消费者....." + this.name);
            flag = false;
//            notifyAll();
            producer_con.signal();
        }finally {
            lock.unlock();
        }
    }
}

class Producer implements Runnable {
    private Resource resource;

    Producer(Resource r) {
        this.resource = r;
    }

    @Override
    public void run() {
        while (true) {
            resource.set("烤鸭");
        }
    }
}

class Consumer implements Runnable {
    private Resource resource;

    Consumer(Resource r) {
        this.resource = r;
    }

    @Override
    public void run() {
        while (true) {
            resource.out("烤鸭");
        }
    }
}



