package concurrent.synchronized_demo_023;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/*
 * Queue
 * ArrayBlockingQueue:数组形式的阻塞队列；有界队列
 *
 */
public class DemoClass5_ArrayBlockingQueue {
    static BlockingQueue<String> abq = new ArrayBlockingQueue<>(10);

    public static void main(String[] args)throws Exception {
        for (int i = 0; i < 10; i++) {
            abq.put("a"+i);
        }

//        abq.put("aaa");//程序阻塞，线程会一直等待
//        abq.add("aaa");//会报错，因为超出了数组的最大容量
//        abq.offer("aaa");//不会报错，但不会插入成功；会返回boolean
        abq.offer("aaa",1, TimeUnit.SECONDS);//等待指定时间后插不进去，就不往里加了
        System.out.println(abq);
    }
}
