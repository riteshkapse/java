package quiz;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 100 Object instances
 * Two methods each M1() and M2()
 * 
 * Thread pool for 50 thread
 * 
 * Need to start execute M2() only after all M1 have completed
 * @author user
 *
 */


public class ThreadPoolProblem2 {

	public static void main(String[] args) throws InterruptedException {
		
		MyObject3[] list = new MyObject3[100];
		Future<Integer>[] status = new Future[100];
		
		IntStream.range(0, 100).forEach((int count) -> {
			list[count] = new MyObject3();
		});
		
		
		ExecutorService es = Executors.newFixedThreadPool(50);
		
		
		//Callable to call M1's of 100 objects
		IntStream.range(0, 100).forEach((int indx) ->{
			status[indx] = es.submit(() -> {				
				list[0].M1(indx);
				return 1;
			});		
			
		});
		
				
		//Check status of M1 call completion - block if necessary
		IntStream.range(0, 100).forEach((int indx) ->{			
			//Block for all M1 call (thread) to stop (if needed)
			try {
				status[indx].get();
			} catch (InterruptedException e) {				
				e.printStackTrace();
			} catch (ExecutionException e) {				
				e.printStackTrace();
			}			
		});
		
		
		//Runnable to run M2's of 100 objects
		IntStream.range(0, 100).forEach((int indx) ->{
			es.submit(() -> {				
				list[0].M2(indx);				
			});
		});
		
		es.shutdown();
		es.awaitTermination(10, TimeUnit.SECONDS);
		
		
		
	}

}
