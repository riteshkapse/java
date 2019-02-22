package quiz;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
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
class MyObject4 {
	
	public void M1(int indx){
		System.out.println("M1:"+indx+" by "+Thread.currentThread().getName());		
	}
	
	public void M2(int indx){
		System.out.println("M2:"+indx+" by "+Thread.currentThread().getName());		
	}
}

public class ThreadPoolProblem3 {

	public static void main(String[] args) {
		
		/*
		
		//Unlimited thread
		
		Object lock = new Object();
		ExecutorService es = Executors.newFixedThreadPool(300);
		
		IntStream.range(0, 300).forEach((int indx) -> {
			MyObject4 obj = new MyObject4();
			es.submit(()->{
				obj.M1(indx);
				
				
				if(indx == 299){ //300th object, time to call all M2 s
					synchronized(lock){
						lock.notifyAll();
					}
				}else{
					try {
						synchronized(lock){
							lock.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				obj.M2(indx);
			});
		});
		
		es.shutdown(); */
		
		
		AtomicBoolean done = new AtomicBoolean(false);
		
		ExecutorService es = Executors.newFixedThreadPool(30);
		MyObject4[] list = new MyObject4[300];
		
		//Create 300 objects, Call M1
		IntStream.range(0, 300).forEach((int indx) -> {
			MyObject4 obj = new MyObject4();
			list[indx] = obj;	
			//Execute M1 right away
			es.submit(()->{
				list[indx].M1(indx);
				if(indx == 299){
					done.set(true);
				}
			});
		});
		
		//Call M2
		IntStream.range(0, 300).forEach((int indx) -> {
			
				while(!done.get()){
					
				}
				
				es.submit(()->{
					list[indx].M2(indx);
				});
						
		});
		
		es.shutdown();

	}

}
