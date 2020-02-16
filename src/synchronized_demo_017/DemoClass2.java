package synchronized_demo_017;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素个数，当个数到5个，线程2提示并结束
 *
 * 方法一中，t1的死循环很浪费cpu
 *
 * 这里使用wait和notify，wait会释放锁，而notify不会释放锁
 * 运用这种方法，必须保证t2先运行
 *
 * 下面的这种方法输出结果并不是size=5时t2退出，而是t1结束了t2才收到通知退出，为何？
 */
public class DemoClass2 {

    volatile List list = new ArrayList();

    void add(Object o){
        list.add(o);
    }

    int size(){
        return list.size();
    }

    public static void main(String[] args) {
        DemoClass2 c = new DemoClass2();
        final Object lock = new Object();

        new Thread(()->{
            System.out.println("t2启动");
            synchronized (lock){
                if (c.size() != 5){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("t2结束");
        },"t2").start();

        new Thread(()->{
            System.out.println("t1启动");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    c.add(new Object());
                    System.out.println("add " + i);
                    if (c.size() == 5){
                        lock.notify();
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"t1").start();

    }

}
