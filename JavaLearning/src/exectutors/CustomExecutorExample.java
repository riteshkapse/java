package exectutors;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;


/*
 * Custom Executor (similar to FixedThreadPoolExecutor)
 */
class MyExecutor {
	
	InnerThread[] threads;
	BlockingQueue<Runnable> queue;
	
	/*
	 * Specify size while creating thread pool. Same size for pool and blocking queue.
	 */
	public MyExecutor(int size) {
		this.threads = new InnerThread[size];
		this.queue = new ArrayBlockingQueue<>(size);
		
		for( int i=0;i<threads.length;i++){ //foreach loop doesn't changes reference (only copy), hence regular for loop			
			InnerThread t = new InnerThread(queue);
			threads[i] = t;
			t.start();					
		}
	}
	
	/*
	 * Submit new taks/ runnable
	 */
	public void submit(Runnable runnable){		
		try {
			this.queue.put(runnable);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	/*
	 * Shutdown will just interrupt all running threads
	 */
	public void shutdown(){
		int interruptCount = 0;
		while(interruptCount != threads.length){
			for(InnerThread t: threads){	
				if(t!=null && t.isAlive()){
					t.interrupt();						
					interruptCount++;
				}							
			}
		}		
	}
}


/*
 * These will be inner threads used by MyExecutor. All instance will share a BlockingQueue which will maintain task list (Runnables).
 * BlockingQueue is synchronized, hence one thread would be able to take one task. If no tasks present thread waits. If task list full new task waits. 
 */
class InnerThread extends Thread{
	
	BlockingQueue<Runnable> queue;
	
	public InnerThread(BlockingQueue<Runnable> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {		
		/*
		 * Run infinite until intruppted
		 */
		while(true){
			try {
				Runnable task = this.queue.take();
				task.run();	
			} catch (InterruptedException e) {
				break;
			}
		}
		
	}
	
}

/*
 * Main clas to test our custom executors 
 */
public class CustomExecutorExample {

	public static void main(String[] args) {
		
		MyExecutor me = new MyExecutor(10);
		Object lock = new Object();

		//10 thread, 20 tasks
		IntStream.range(1, 21).forEach((int count)->{
			me.submit( () -> {				
				if(count== 2 || count ==4){
					try {						
						synchronized (lock) {
							System.out.println(Thread.currentThread().getName()+" waiting for "+count);
							lock.wait();
						}						
					} catch (InterruptedException e) {						
						System.out.println(Thread.currentThread().getName()+" intrrupted while waiting...");
					}
				}				
				System.out.println("Running count:"+count+ " by "+Thread.currentThread().getName());
			} );
		});		
				
		me.shutdown();
	}

}
