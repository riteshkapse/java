package quiz;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class CustomThreadPoolExecutor {

	BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100);;
	LongLivedThread[] executors = null;
	volatile boolean keepAcceptingTask = true;

	public CustomThreadPoolExecutor(int thCount) {
		this.executors = new LongLivedThread[thCount];
		// prepare threads
		for (int count = 0; count < thCount; count++) {
			this.executors[count] = new LongLivedThread(queue);
			this.executors[count].start(); // start the thread
		}
	}

	public void execute(Runnable runnable) {
		if (keepAcceptingTask) {
			queue.add(runnable); // Exception if capicity exceeds
		}

	}

	public void shutdown() { // Dont take new tasks
		keepAcceptingTask = false; // No new task will be executed
		// Stop running threads
		for (LongLivedThread th : this.executors) {
			th.stopThread();
		}
	}

	public void awaitTermination(int i, TimeUnit milliseconds) {
		if(keepAcceptingTask == false){ //shutdown requested
			try {
				milliseconds.sleep(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				for (LongLivedThread th : this.executors) {
					th.stop();
				}
			}
		}		
	}
}

class LongLivedThread extends Thread {

	BlockingQueue<Runnable> queue;
	volatile boolean shutdownRequested = false;

	public LongLivedThread(BlockingQueue<Runnable> queue) {
		this.queue = queue;
	}

	public void stopThread() {
		shutdownRequested = true;
	}

	@Override
	public void run() {
		while (!(shutdownRequested && queue.isEmpty())) {

			Runnable runnable = queue.poll(); // Do not block (as we need to check for shutdown request)
			if (runnable != null) {
				runnable.run(); // Running thread
			}

		}
	}

}

public class CustomThreadPoolExecutorExample {

	public static void main(String[] args) {

		CustomThreadPoolExecutor es = new CustomThreadPoolExecutor(4);

		IntStream.range(0, 10).forEach((int indx) -> {
			es.execute(() -> {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Working on task " + indx + " by " + Thread.currentThread().getName());
			});
		});

		es.shutdown();
		
		es.awaitTermination(500, TimeUnit.MILLISECONDS);
		

		es.execute(() -> {
			System.out.println("Working on task after shutdown by " + Thread.currentThread().getName());
		});

	}

}
