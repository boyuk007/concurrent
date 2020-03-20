package concurrent.synchronized_demo_005;

public class DemoClass implements Runnable {

    private int count = 10;

    @Override
    public /*synchronized*/ void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void main(String[] args) {
        DemoClass t = new DemoClass();
        for (int i = 0; i < 5; i++) {
            new Thread(t,"Thread" + i).start();
        }
    }
}
