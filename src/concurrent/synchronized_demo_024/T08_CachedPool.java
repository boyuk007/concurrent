package concurrent.synchronized_demo_024;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CachedThreadPool():当任务来时，当前线程池有空闲的线程，使用空闲的线程，没有则创建；
 * 					最大线程数为系统所能支撑的最大线程数；
 * 					空闲线程的有存活时间，默认60秒；
 */

public class T08_CachedPool {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newCachedThreadPool();
		System.out.println(service);
		
		for (int i = 0; i < 2; i++) {
			service.execute(() -> {
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			});
		}
		
		System.out.println(service);
		
		TimeUnit.SECONDS.sleep(80);
		
		System.out.println(service);
		
		
	}
}
