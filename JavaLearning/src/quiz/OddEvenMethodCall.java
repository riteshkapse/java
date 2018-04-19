package quiz;

import java.util.concurrent.atomic.AtomicInteger;


class OddEven {	
	public void Odd(String thread){
		System.out.println(thread +" executing Odd...");
	}
	public void Even(String thread){
		System.out.println(thread +" executing Even...");
	}
	
}

/*
 * Single threaded solution
 */
public class OddEvenMethodCall {

	public static void main(String[] args) {
		
		OddEven obj = new OddEven();		
		AtomicInteger count = new AtomicInteger(0);
		
		Thread oddThread = new Thread( () -> {
			while(count.get() <100){
				synchronized(obj){
					if(count.get()%2 == 0){
						obj.Odd(Thread.currentThread().getName());
						count.incrementAndGet();
					}
				}
			}
		});
		
		Thread evenThread = new Thread( () -> {
			while(count.get() < 100){
				synchronized (obj){
					if(count.get()%2 != 0){
						obj.Even(Thread.currentThread().getName());
						count.incrementAndGet();
					}
				}
			}
		});
		
		oddThread.start();
		evenThread.start();
		
		System.out.println("Main exits!!!");
		
	}

}
