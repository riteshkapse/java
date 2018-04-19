package locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class ReadWriteLockExample {
	
	
	ReadWriteLock lock = new ReentrantReadWriteLock();

	private int count = 0;
	
	public void increment(){	
		lock.writeLock().lock(); // Exclusive lock for write
		count++;	
		lock.writeLock().unlock();
	}
	
	public void print(){
		lock.readLock().lock(); //Read can happen parallaly. No read if count being writeen.
		System.out.print(count +" ");
		lock.readLock().unlock();
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		
		ReadWriteLockExample obj = new ReadWriteLockExample();
		
		ExecutorService es = Executors.newFixedThreadPool(10);
		
		IntStream.range(1, 1001).forEach((int count) -> {
			es.submit(()->{
				obj.increment();
				
				//Sleep for some time
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			});
		});
		
		IntStream.range(1, 1001).forEach((int count) -> {
			es.submit(()->{
				obj.print();
				
				//Sleep for some time
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			});
		});
		
		es.shutdown();
		es.awaitTermination(10, TimeUnit.SECONDS);
		
		System.out.println("\n\nCurrent count:"+obj.count);
		

	}

}
