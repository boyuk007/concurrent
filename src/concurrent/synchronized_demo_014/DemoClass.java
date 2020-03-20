package concurrent.synchronized_demo_014;

import java.util.concurrent.TimeUnit;

/*
 * synchronized优化
 * 同步代码块中的语句越少越好
 */
public class DemoClass {
    int count = 0;

    synchronized void m1() {
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //业务逻辑中只有下面需要加锁，不应该整个方法加锁
        count++;
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    void m2() {
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //采用细粒度的锁，可以使线程争用时间变短，从而提高效率
        synchronized (this) {
            count++;
        }
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
