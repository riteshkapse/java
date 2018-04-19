package locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLockExample {

	private int count = 0;
	
	ReentrantLock lock = new ReentrantLock();
	
	public void increment(){	
		lock.lock(); //Only one thread running inbetween		
		count++;		
		lock.unlock();
		print(count);
	}
	
	
	private void print(int count) {
		boolean gotLock = lock.tryLock();
		System.out.print(count+" ");
		if(!gotLock){
			System.out.println("\nNO LOCK!!!");
		}else{
			lock.unlock();
		}
	}


	public static void main(String[] args) throws InterruptedException {
		
		ReentrantLockExample obj = new ReentrantLockExample();
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		
		IntStream.range(1, 1001).forEach((int count) -> {
			es.submit(()->{
				obj.increment();
			});
		});
		
		es.shutdown();
		es.awaitTermination(10, TimeUnit.SECONDS);
		
		System.out.println("\n\nCurrent count:"+obj.count);
		

	}

}
