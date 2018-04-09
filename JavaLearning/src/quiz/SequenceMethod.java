package quiz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * 
 * @author Ritesh Kapse
 * 
 * Write a program in which each thread call objects methods in sequence. Meke sure sequence of thread creation
 * and sequence of method execution is maintained.
 *
 */

class MyObject {
	public volatile static String lastExcecuted = "";
	
	public synchronized static void M1(){
		System.out.println("M1 executed...");
	}
	
	public synchronized static void M2(){
		System.out.println("M2 executed...");
	}
	
	public synchronized static void M3(){
		System.out.println("M3 executed...");
	}
}

public class SequenceMethod {
	
	public static void main(String[] args) throws InterruptedException {
			
		
		//ExecutorService es = Executors.newSingleThreadExecutor(); //Not concurrent
		ExecutorService es = Executors.newFixedThreadPool(3);
		
		
		//Submit tasks to threads
		IntStream.range(1, 10).forEach( (int count) -> {
			es.submit(() -> {
				synchronized (MyObject.class) {
					if(MyObject.lastExcecuted.equals("M1")){
						MyObject.M2();
						MyObject.lastExcecuted = "M2";
					}else if(MyObject.lastExcecuted.equals("M2")){
						MyObject.M3();
						MyObject.lastExcecuted = "M3";
					}else{
						MyObject.M1();
						MyObject.lastExcecuted = "M1";
					}
				}
				
			});
		});
		
		es.shutdown();
		
		
	}

}
