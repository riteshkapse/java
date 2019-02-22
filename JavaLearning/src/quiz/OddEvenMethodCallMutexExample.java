package quiz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class OddEvenMethodCallMutexExample {

	static boolean odd = false;
	
	public static void main(String[] args) {
		
		OddEven obj = new OddEven();		
		ExecutorService es = Executors.newFixedThreadPool(5);
		Semaphore mutex = new Semaphore(1);
		
		//AtomicBoolean odd = new AtomicBoolean(false);
		
		
		
		IntStream.range(0, 101).forEach((int count)->{			
			
			es.submit(()->{
				//Runnable
				boolean access = false;
				
				while(access !=true){
					access = mutex.tryAcquire();
					
					//Sleep for sometime if permits not acquired
					if(!access){
						try {
							TimeUnit.MILLISECONDS.sleep(10);
						} catch (InterruptedException e) {							
							e.printStackTrace();
						}finally{
							continue; //try to acquire again
						}
					}
					
					//got permit. Single thread have exclusive access to below code
					if(odd){
						obj.Odd(Thread.currentThread().getName());						
						odd = false;
					}else{
						obj.Even(Thread.currentThread().getName());
						odd = true;
					}
					
					mutex.release();
					
				}
				
				
				}
			);
		});
		
		es.shutdown();

	}

}
