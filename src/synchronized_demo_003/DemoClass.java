package synchronized_demo_003;

public class DemoClass {

    private int count = 10;

    public synchronized void m(){//等同于在方法的代码执行时要synchronized(this)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}
