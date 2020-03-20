package concurrent.synchronized_demo_015;

import java.util.concurrent.TimeUnit;

/*
 * 锁定某对象o，如果o的属性发生改变，不影响锁的使用
 * 但是如果o变成了另一个对象，则锁定的对象发生改变
 * 应该避免将锁定对象的引用变成另外的对象
 */
public class DemoClass {
    Object o = new Object();

    void m1() {
        synchronized(o){
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        DemoClass t = new DemoClass();
        //启动第一个线程
        new Thread(()->t.m1(),"t1").start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //启动第二个线程
        Thread t2 = new Thread(() -> t.m1(), "t2");
//        t.o = new Object();
        t2.start();
    }

}
