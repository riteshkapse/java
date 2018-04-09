package quiz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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

class MyObject3 {
	
	public void M1(int indx){
		System.out.println("M1:"+indx+" by "+Thread.currentThread().getName());		
	}
	
	public void M2(int indx){
		System.out.println("M2:"+indx+" by "+Thread.currentThread().getName());		
	}
}

public class ThreadPoolProblem {

	public static void main(String[] args) throws InterruptedException {
		
		MyObject3[] list = new MyObject3[100];
		AtomicInteger callCount = new AtomicInteger(0);
		
		IntStream.range(0, 100).forEach((int count) -> {
			list[count] = new MyObject3();
		});
		
		
		ExecutorService es = Executors.newFixedThreadPool(50);
		
		IntStream.range(0, 100).forEach((int indx) ->{
			es.submit(() -> {
					list[0].M1(indx);
					callCount.incrementAndGet();
			});	
		});		
		
		//Wait till all M1 calls are done
		System.out.println("Waiting for M1 to be finished");
		do{
			//System.out.print(".");
		}while(callCount.get() != 100);
		
		
		IntStream.range(0, 100).forEach((int indx) ->{
			es.submit(() -> {
					list[0].M2(indx);
					callCount.incrementAndGet();
			});	
		});
		
		System.out.println("Waiting for M2 calls to be finished");
		do{
			//System.out.print(".");
		}while(callCount.get() != 200);
			
		
		es.shutdown();				
		boolean status = es.awaitTermination(100, TimeUnit.MINUTES);
		
		System.out.println("Executor terminated :"+status);
		
		
	}

}
