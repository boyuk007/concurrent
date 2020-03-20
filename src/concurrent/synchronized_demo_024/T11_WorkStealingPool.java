/**
 *
 */
package concurrent.synchronized_demo_024;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * WorkStealingPool():默认根据cpu是几核，就启动多少个线程；
 * 					作用：能保证任务的顺序执行。
 */

public class T11_WorkStealingPool {
	public static void main(String[] args) throws IOException {
		ExecutorService service = Executors.newWorkStealingPool();
		System.out.println(Runtime.getRuntime().availableProcessors());//查看cpu是几核

		service.execute(new R(1000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000)); //daemon
		service.execute(new R(2000));

		//由于产生的是精灵线程（守护线程、后台线程）主线程不阻塞的话，看不到输出
		System.in.read();
	}

	static class R implements Runnable {

		int time;

		R(int t) {
			this.time = t;
		}

		@Override
		public void run() {
			
			try {
				TimeUnit.MILLISECONDS.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(time  + " " + Thread.currentThread().getName());
			
		}

	}
}
