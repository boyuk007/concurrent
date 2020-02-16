package synchronized_demo_007;

import java.util.concurrent.TimeUnit;

/*
 *对业务写方法加锁
 *对业务读方法不加锁
 * 容易产生数据的脏读（dirtyRead）
 */
public class DemoClass {
    String name;
    double balance;

    public synchronized void set(String name,double balance) {
        this.name = name;
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public double getBalance(String name) {
        return this.balance;
    }

    public static void main(String[] args) {
        DemoClass t = new DemoClass();

        new Thread(()->t.set("zhnagsan",100.0)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(t.getBalance("zhnagsan"));

        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(t.getBalance("zhnagsan"));
    }
}
