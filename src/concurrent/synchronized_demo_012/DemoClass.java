package concurrent.synchronized_demo_012;

import java.util.ArrayList;

/*
 * volatile并不能保证多个线程共同修改running变量时所带来的不一致的问题，也就是说volatile不能代替synchronized
 * 加上synchronized可以解决
 */
public class DemoClass {
    volatile int count = 0;

    public /*synchronized*/ void m1() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        DemoClass t = new DemoClass();

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(()->t.m1(),"thread-" + i));
        }

        threads.forEach((o)->o.start());

        threads.forEach((o)->{
            try {
                o.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });

        System.out.println(t.count);
    }
}
