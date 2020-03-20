package concurrent.synchronized_demo_018;

import java.util.concurrent.TimeUnit;

/*
 * 多生产者多消费者问题
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

    public synchronized void set(String name) {
//        if (flag){
        while (flag) {//使用if，被唤醒后悔直接往下执行，不会再进行判断，造成线程安全问题；使用while强制每次唤醒都进行判断
            try {
                this.wait();
            } catch (InterruptedException e) {

            }
        }
        this.name = name + count;
        count++;
        try { TimeUnit.MILLISECONDS.sleep(10); } catch (InterruptedException e) { }
        System.out.println(Thread.currentThread().getName() + "...生产者..." + this.name);
        flag = true;
//        notify();
        notifyAll();//使用notify，有可能唤醒的是生产者，会造成死锁
    }

    public synchronized void out(String name) {
//        if (!flag){
        while (!flag) {
            try {
                this.wait();
            } catch (InterruptedException e) {

            }
        }
        try { TimeUnit.MILLISECONDS.sleep(10); } catch (InterruptedException e) { }
        System.out.println(Thread.currentThread().getName() + "...消费者....." + this.name);
        flag = false;
//        notify();
        notifyAll();
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



