package locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaphoreExample {
	
	int count = 0;

	public static void main(String[] args) throws InterruptedException {
		
		SemaphoreExample example = new SemaphoreExample();
		ExecutorService es = Executors.newFixedThreadPool(3);
		Semaphore semaphore = new Semaphore(1);
		
		
		IntStream.range(1, 10001).forEach((int indx)->{
			
			es.submit(()->{
				try {
					semaphore.acquire();
					example.count++;
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}finally {
					semaphore.release();
				}
				
			});						
		});

		
		es.shutdown();
		es.awaitTermination(10, TimeUnit.SECONDS);
		
		System.out.println("Final count:"+example.count);
	}

}
