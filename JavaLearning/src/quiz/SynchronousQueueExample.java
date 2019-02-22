package quiz;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueExample {

	public static void main(String[] args) throws InterruptedException {
		
		BlockingQueue synchronousQueue = new SynchronousQueue<String>();
		
		System.out.println("1:Queue size:"+synchronousQueue.size());
		
		
		Thread th = new Thread(()->{
			try {
				System.out.println("Thread th took: "+synchronousQueue.take());
			} catch (InterruptedException e) {				
			}
		});
		
		th.setDaemon(true); //lazy start
		th.start();
		
		
		//Put is blocked until thread th executes take
		synchronousQueue.put("Hello");
		
		System.out.println("2:Queue size:"+synchronousQueue.size());
		
		
		synchronousQueue.take(); //blocks until any thread call put
		
		System.out.println("3:Queue size:"+synchronousQueue.size());
		

	}

}
