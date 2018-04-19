package exectutors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomExecutorExample {

	public static void main(String[] args) throws InterruptedException {
		
		long starttime = System.currentTimeMillis();
		
		ExecutorService es = new ThreadPoolExecutor(2, Runtime.getRuntime().availableProcessors(), 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4));
		
		es.submit(() ->{
			System.out.println("Running runnable....");
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
		});
			
		es.shutdown();
						
		es.awaitTermination(20, TimeUnit.SECONDS);
		long endtime = System.currentTimeMillis();
		
		System.out.println("Execution time:"+ TimeUnit.MILLISECONDS.toSeconds(endtime-starttime));
	}

}
