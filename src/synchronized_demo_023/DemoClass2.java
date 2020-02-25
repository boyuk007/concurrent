package synchronized_demo_023;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/*
 * 写时复制容器 copy on write
 * 多线程下，写时效率低，读时效率高
 *
 */
public class DemoClass2 {
    public static void main(String[] args) {
//        List<String> li = new ArrayList<>();//这个单独使用并发会有问题；可加锁使用
//        List<String> list = Collections.synchronizedList(li);
//        List<String> list = new Vector<>();
        List<String> list = new CopyOnWriteArrayList<>();

        Random random = new Random();
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    list.add("a" + random.nextInt(10000));
                }
            });
        }

        long start = System.currentTimeMillis();
        Arrays.asList(threads).forEach(t -> t.start());
        Arrays.asList(threads).forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(list.size());
    }
}
