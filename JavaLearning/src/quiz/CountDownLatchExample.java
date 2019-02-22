package quiz;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


public class CountDownLatchExample {

	private static final int noOfParties = 100; 
	
	public static void main(String[] args) {
		
		CountDownLatch countDownLatch = new CountDownLatch(noOfParties);
		
		ExecutorService es = Executors.newFixedThreadPool(100);		
		
		IntStream.range(0, noOfParties).forEach((int index)->{
			es.submit(()->{
				
				OddEvenn obj = new OddEvenn();
				
				obj.odd(Thread.currentThread().getId());
				
				countDownLatch.countDown();
				
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
				
				obj.even(Thread.currentThread().getId());
				
			});
		});
		
		es.shutdown();	

	}

}
