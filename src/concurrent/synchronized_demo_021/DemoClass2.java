package concurrent.synchronized_demo_021;

import java.util.concurrent.TimeUnit;

/*
 * ThreadLocal 线程局部变量
 *
 * Thread是使用空间换时间，synchronized是使用时间换空间
 *
 */
public class DemoClass2 {
    static ThreadLocal<String> tl = new ThreadLocal();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tl.get());
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tl.set("zhangsan");
        }).start();
    }
}
