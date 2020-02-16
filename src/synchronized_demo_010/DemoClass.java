package synchronized_demo_010;

import java.util.concurrent.TimeUnit;

/*
 *程序在运行过程中，如果发生异常，默认锁会被释放掉
 * 如果数据处理了一半，发生异常，别的线程拿到锁后，就会读到未处理完的数据
 */
public class DemoClass {
    int count = 0;

    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + " m1 start.... " );
        while (true) {
            count++;
            System.out.println(Thread.currentThread().getName() + " count= " + count);
            try {
                TimeUnit.SECONDS.sleep(1);
//                if (count == 5) {
//                    int i = 1 / 0;//此处抛出异常，锁将被释放，要想不被释放，可以在这里进行catch，然后继续循环
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (count == 5) {
                int i = 1 / 0;//此处抛出异常，锁将被释放，要想不被释放，可以在这里进行catch，然后继续循环
            }
        }
    }

    public static void main(String[] args) {
        DemoClass t = new DemoClass();

        new Thread(()->t.m1(),"t1").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        new Thread(()->t.m1(),"t2").start();
    }
}
