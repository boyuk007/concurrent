package concurrent.synchronized_demo_023;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/*
 *线程容器 :ConcurrentHashMap
 *
 * Hashtable：每次往容器里插入数据时，会锁住整个容器；
 * ConcurrentHashMap：把整个容器分成了16段，每次往里插入，只会锁住当前的小段；
 *
 */
public class DemoClass {

    public static void main(String[] args) {
        Map<String, String> map = new ConcurrentHashMap<>();
//        Map<String, String> map = new ConcurrentSkipListMap<>();//高并发并且需排序
//        Map<String, String> map = new Hashtable<>();
//        Map<String, String> map = new HashMap<>();//Collections.synchronizedXX

        Random random = new Random();
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);
        long start = System.currentTimeMillis();

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    map.put("a" + random.nextInt(100000), "a" + random.nextInt(100000));
                    latch.countDown();
                }
            });
        }

        Arrays.asList(threads).forEach(t -> t.start());
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
