package quiz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 100 Object instances
 * Two methods each T1() and T2()
 * 
 * Thread pool for 50 thread
 * 
 * Need to start execute T2() only after all T1 have completed
 * @author user
 *
 */

class MyObjectX implements Runnable{
	
	AtomicBoolean t1Done;
	
	public MyObjectX(AtomicBoolean t1Done) {
		this.t1Done = t1Done;
	}
	
	public void T1(){
		System.out.println("Running T1 by "+Thread.currentThread().getName());
		
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}		
	}

	public void T2(){
		System.out.println("Running T2 by "+Thread.currentThread().getName());
	}
	
	
	@Override
	public void run() {
		
		if(this.t1Done.get()){
			T2();
		}else{
			T1();
		}		
		
	}
	
	
	
	
}
public class ThreadPoolProblem {

	public static void main(String[] args) {
		//100 thread, 50 runing threads
		ExecutorService es = Executors.newFixedThreadPool(50);
		AtomicBoolean t1Done = new AtomicBoolean(false);
		for(int i=1; i<=100; i++){			
			MyObjectX obj = new MyObjectX(t1Done);
			es.submit(obj);			
		}

	}

}
