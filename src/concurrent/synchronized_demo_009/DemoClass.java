package concurrent.synchronized_demo_009;
/*
 *一个同步方法可以调用另一个同步方法，一个线程已经拥有某个对象的锁，可以再次申请获取该对象的锁
 * 也就是说synchronized获得的锁是可以重入的
 * 子类调用父类的同步方法
 */
public class DemoClass {

    synchronized void m1() {
        System.out.println(" m1 start.... " );
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(" m1 end.... " );
    }

    public static void main(String[] args) {
        new DemoclassSon().m1();
    }
}

class DemoclassSon extends DemoClass{
    @Override
    synchronized void m1(){
        System.out.println("child m1 start");
        super.m1();
        System.out.println("child m1 end");
    }

}
