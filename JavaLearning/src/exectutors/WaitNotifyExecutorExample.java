package exectutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class WaitNotifyExecutorExample {

	public static void main(String[] args) throws InterruptedException {

		ExecutorService es = Executors.newFixedThreadPool(2);
		Object lock = new Object();

		IntStream.range(1, 11).forEach((int count) -> { // 10 task/runnables are submitted
			
			es.submit(() -> {
				
				System.out.println(Thread.currentThread().getName() + " processing count " + count);
				// do something
				if (count == 2 || count == 4) {
					// Runnable 2 and 4 sleeps till 10 mins
					try {
						// TimeUnit.MINUTES.sleep(10); // <-- Sleep keep the
						// runnable with the trhead in pool
						System.out.println(Thread.currentThread().getName() + " WAITING with count " + count);
						synchronized (lock) {
							lock.wait(); // <-- Waiting runnables put the
											// current executing thread in wait
											// state
						}

					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						synchronized (lock) {
							System.out.println(Thread.currentThread().getName() + " NOTIFIYING with count " + count);
							lock.notify();// <-- Waiting runnables pushed out of
											// pool. This is to notify another thread (e.g. count = 4)							
						}
					}
				}

				// Keep thread engaged for 1 minute
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println(Thread.currentThread().getName() + " done with count " + count);
				}

			});
		});

		es.shutdown();
		es.awaitTermination(10, TimeUnit.SECONDS);

		synchronized (lock) {
			System.out.println(Thread.currentThread().getName() + " NOTIFYING" );
			lock.notifyAll();
		}

	}

}
