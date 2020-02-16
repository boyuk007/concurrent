package synchronized_demo_008;
/*
 *一个同步方法可以调用另一个同步方法，一个线程已经拥有某个对象的锁，可以再次申请获取该对象的锁
 * 也就是说synchronized获得的锁是可以重入的
 */
public class DemoClass {

    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + " m1 start.... " );
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        m2();
        System.out.println(Thread.currentThread().getName() + " m1 end.... " );
    }

    public synchronized void m2() {
        System.out.println(Thread.currentThread().getName() + " m2 start.... " );
        try{
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m2 end.... " );
    }

    public static void main(String[] args) {
        DemoClass t = new DemoClass();

        new Thread(()->t.m1(),"t1").start();
    }
}
