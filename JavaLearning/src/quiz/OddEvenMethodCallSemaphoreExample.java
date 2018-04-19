package quiz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

/**
 * This example uses two semaphores, one for even and one for odd turn permit.
 * Mutliple thread might try to acquire odd or even semaphore simultaneously. 
 * To maintain a sequence Even() method run and release odd permit and vice versa. 
 * If no permits can be acquired by a thread, they will try again (infinite loop for runnable)
 * 
 * @author user
 *
 */

public class OddEvenMethodCallSemaphoreExample {

	
	public static void main(String[] args) {
		
		OddEven obj = new OddEven();
		
		ExecutorService es = Executors.newFixedThreadPool(5);	
		
		Semaphore evenSemaphore = new Semaphore(1); //Start with even i.e. 0
		Semaphore oddSemaphore = new Semaphore(0); //No permit to start with
				
				
		IntStream.range(0, 11).forEach((int count)->{
			
			es.submit(()->{
				
				while(true){ //Dont stop untill work done
					
					boolean evenAquired = evenSemaphore.tryAcquire();
					if(evenAquired){ //call Even() and release odd permit for Odd to execute
						obj.Even(Thread.currentThread().getName()+count);	
						oddSemaphore.release();
						break; //Done with this runnable, break out of loop
					}else{ //Even not aquired, probably odd turn 
						//Two threads might do tryAcuqire simultaneously, this doesn't mean its Odd's turn, Even may still be running.
						boolean oddAquired = oddSemaphore.tryAcquire();
						if(oddAquired){ //call Odd() and release even permit
							obj.Odd(Thread.currentThread().getName()+count);
							evenSemaphore.release();
							break;
						}
					}
				}				
				
			});
			
		});		
		
		es.shutdown();

	}

}
