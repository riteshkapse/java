package synchronizers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Mutexes provide mutual exclusive access to object/resource which is being shared with multiple threads
 * It can be implemented using semaphore with single permit as shown below.
 * @author user
 *
 */
public class MutexExample {

	public static void main(String[] args) {
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		
		Semaphore permits = new Semaphore(1);
		
		
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
					TimeUnit.SECONDS.sleep(1);
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
