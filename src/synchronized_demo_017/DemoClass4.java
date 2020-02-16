package synchronized_demo_017;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
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
 * notify之后，t1必须释放锁，t2退出后，也必须notify，通知t1继续执行
 * 整个过程比较繁琐
 *
 * 使用Latch（门闩）代替wait notify来进行通知
 * 好处是通信方式简单，同时也可以指定等待时间
 * 使用await和countdown方法代替wait和notify
 * CountDownLatch不涉及锁定，当count的值为0时当前线程继续运行
 * 当不涉及同步，只是涉及线程通信的时候，用synchronized+wait/notify就显得太重了
 * 这时应该考虑countdownlatch/cyclicbarrier/semaphore
 */
public class DemoClass4 {

    volatile List list = new ArrayList();

    void add(Object o){
        list.add(o);
    }

    int size(){
        return list.size();
    }

    public static void main(String[] args) {
        DemoClass4 c = new DemoClass4();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(()->{
            System.out.println("t2启动");
            if (c.size() != 5){
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t2结束");
        },"t2").start();

        new Thread(()->{
            System.out.println("t1启动");
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add " + i);
                if (c.size() == 5){
                    countDownLatch.countDown();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();

    }

}
