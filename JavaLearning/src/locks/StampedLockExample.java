package locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

/**
 * StampedLock lock returns a stampId which need to be used to unlock it. It is not re-entrant unlike 
 * ReadWriteLock. ReadOptimistic is fast version of ReadLock. It will not block write lock unlike other locks. 
 * Write lock is usually aquired after current running reads are done. 
 * @author user
 *
 */
public class StampedLockExample {
	
	private int count = 0;

	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService es = Executors.newFixedThreadPool(10);		
		StampedLockExample obj = new StampedLockExample();
		StampedLock lock = new StampedLock();
		
		//Write lock
		IntStream.range(1, 101).forEach((int count)->{
			es.submit(()->{
				long write_stamp = lock.writeLock();
				obj.count++;
				//Sleep a bit
				try {
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					lock.unlockWrite(write_stamp);
				}
				
			});
		});
		
		//Read lock
		ExecutorService es2 = Executors.newWorkStealingPool();
		IntStream.range(1, 100001).forEach((int count)->{
			es2.submit(()->{
				long read_stamp = lock.readLock();
				int i = obj.count;
				i++;
				lock.unlockRead(read_stamp);
			});
		});
		
		
		//Optimistic Read
		ExecutorService es3 = Executors.newFixedThreadPool(10);
		IntStream.range(1, 11).forEach((int count)->{
			es3.submit(()->{
											
				//Sleep a bit
				try {
					TimeUnit.MILLISECONDS.sleep(1000); //Sleep more so that other tasks completes
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
				long stamp3 = lock.tryOptimisticRead();
				
				if(stamp3 == 0){
					System.out.println(""+obj.count+" OptimisticRead failed. Lock valid? "+lock.validate(stamp3));
				}else{
					System.out.println(""+obj.count+" Lock valid? "+lock.validate(stamp3));
					lock.unlockRead(stamp3);
				}				
				
			});
		});
		
		
		
		es.shutdown();
		es.awaitTermination(20, TimeUnit.SECONDS);
		
		es2.shutdown();
		es2.awaitTermination(20, TimeUnit.SECONDS);
		
		es3.shutdown();
		es3.awaitTermination(20, TimeUnit.SECONDS);
		
		System.out.println("Main count:"+obj.count);

	}

}
