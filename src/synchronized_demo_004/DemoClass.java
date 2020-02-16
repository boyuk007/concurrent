package synchronized_demo_004;

public class DemoClass {

    private static int count = 10;

    public synchronized static void m(){//等同于synchronized (DemoClass.class)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public void mm(){
        synchronized (DemoClass.class){
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }
}
