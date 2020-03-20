package concurrent.synchronized_demo_006;
/*
 *同步异步问题
 */
public class DemoClass {

    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + " m1 start.... " );
        try{
            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m1 end.... " );
    }

    public void m2() {
        System.out.println(Thread.currentThread().getName() + " m2 start.... " );
        try{
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m2 end.... " );
    }

    public static void main(String[] args) {
        DemoClass t = new DemoClass();

        new Thread(()->t.m1(),"t1").start();
        new Thread(()->t.m2(),"t2").start();

//        new Thread(t::m1,"t1").start();
//        new Thread(t::m2,"t2").start();

        //原始写法
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                t.m1();
//            }
//        },"t1").start();
    }
}
