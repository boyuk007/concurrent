package synchronized_demo_011;

import java.util.concurrent.TimeUnit;

/*
 *volatile关键字，使一个变量在多个线程间可见
 *A B线程都用到一个变量，java默认是A线程中保留一份copy，这样如果B线程修改了改变量，则A线程未必知道
 * 使用volatile，会让所有线程都会读到变量的修改值
 *
 * 在下面的代码中，running是存在于堆内存的DemoClass对象中
 * 当线程t1开始运行的时候，会把running值从内存中读到t1线程的工作区，在运行过程中直接使用这个copy，
 * 并不会每次都去取堆内存，这样，当主线程修改running的值之后，t1线程感知不到，所以不会停止运行
 * 使用volatile，将会强制所有线程都去堆内存中读取running的值
 *
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致的问题，也就是说volatile不能代替synchronized
 */
public class DemoClass {
    /*volatile*/ boolean running = true;

    public void m1() {
        System.out.println( " m1 start.... " );
        while (running) {
            //如果死循环里加了一些代码块，cpu这时可能就空闲出来了，有可能就会去内存读取running的值
            /*
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */
        }
        System.out.println(" m1 end.... " );
    }

    public static void main(String[] args) {
        DemoClass t = new DemoClass();

        new Thread(()->t.m1()).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.running = false;
    }
}
