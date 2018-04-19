package quiz;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * This example used single thread to execute even, odd method of same object in sequence.
 * After each method call main thread blocks (till completion) and then another method is called.
 * @author user
 *
 */
public class OddEvenMethodCallSingleThreadExecutor {

	public static void main(String[] args) {
		
		ExecutorService myThread = Executors.newSingleThreadExecutor();
	
		
		OddEven obj = new OddEven();
		
		IntStream.range(0, 11).forEach((int count)->{
			
			if(count%2==0){
				
				Callable a = () -> {
					obj.Odd(Thread.currentThread().getName());
					return null;
				};				
				Collection runnableList = Arrays.asList(a);
	
				try {
					myThread.invokeAll(runnableList); //Block
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
				
			}else{
				
				Callable a = () -> {
					obj.Even(Thread.currentThread().getName());
					return null;
				};				
				Collection runnableList = Arrays.asList(a);
				
				try {
					myThread.invokeAll(runnableList); //Block
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
			}
			
		});
		
		myThread.shutdown();			

	}

}
