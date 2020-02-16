package synchronized_demo_013;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * 解决同样的问题更高效的方法，使用AtomXX类
 * AtomXX类本身方法都是原子性的，但不能保证多个方法连续调用是原子性的
 */
public class DemoClass {
//    volatile int count = 0;
    AtomicInteger count = new AtomicInteger(0);
    void m1() {
        for (int i = 0; i < 10000; i++) {
//            if (count.get() < 1000)//加上这个，count就不能保证原子性了，
// 因为有可能当前线程在这个判断完成之后（count=999），count+1之前，其他的线程已经执行了count+1了（其他线程count=1000），这时当前线程执行count+1就会出错（当前线程count=1001）
            count.incrementAndGet();//替代count++方法，相当于加了synchronized，但并不是用synchronized实现的，并且效率比synchronized高
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
