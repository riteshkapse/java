package synchronizers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Semaphore is used to restrict access to shared resource e.g. only 4 threads can access specific file at one time.
 * It maintains a set of permits. Permits and be acquired (to start use) or released after use.
 * @author Ritesh Kapse
 *
 */
public class SemaphoreExample {

	public static void main(String[] args) {
		
		ExecutorService es = Executors.newFixedThreadPool(10);
		
		Semaphore permits = new Semaphore(4);
		
		
		IntStream.range(1, 101).forEach((int count)->{
			es.submit(() -> {
				
				try {
					permits.acquire();
				} catch (InterruptedException e1) {					
					e1.printStackTrace();
					Thread.currentThread().interrupt();
				}
				
				System.out.println("Starting task for count:"+count+" by thread "+Thread.currentThread().getName());				
				
				//Sleep
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}finally {
					System.out.println("-------------------------------------> Ending task for count:"+count+" by thread "+Thread.currentThread().getName());
					permits.release();
				}
				
			});
		});
		
		es.shutdown();		

	}

}
