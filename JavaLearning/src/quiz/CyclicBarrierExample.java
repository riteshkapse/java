package quiz;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

class OddEvenn{
	public void odd(long id){
		System.out.println("Thread "+id+" printing ODD");
	}
	public void even(long id){
		System.out.println("Thread "+id+" printing EVEN");
	}
}

public class CyclicBarrierExample {
	
	private static final int noOfParties = 100; 

	public static void main(String[] args) {
		
		CyclicBarrier cyclicBarrier = new CyclicBarrier(noOfParties);
		
		ExecutorService es = Executors.newFixedThreadPool(100);		
		
		IntStream.range(0, noOfParties).forEach((int index)->{
			es.submit(()->{
				
				OddEvenn obj = new OddEvenn();
				
				obj.odd(Thread.currentThread().getId());
				
				try {
					cyclicBarrier.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
				
				obj.even(Thread.currentThread().getId());
				
			});
		});
		
		es.shutdown();		

	}

}
