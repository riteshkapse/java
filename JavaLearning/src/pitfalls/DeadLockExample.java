package pitfalls;

import java.util.concurrent.TimeUnit;

public class DeadLockExample {

	public static void main(String[] args) throws InterruptedException {

		Thread main = Thread.currentThread();
		
		Thread T1 = new Thread(()->{
			System.out.println("Running T1");
			try {
				TimeUnit.SECONDS.sleep(5);
				main.join(); // T1 waits for main to completee
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
			System.out.println("Done T1");
		});
		
		
		T1.start();
		T1.join(); // main waits for T1 to complete, resulting in deadlock
		
			
		
		System.out.println("Main terminates....");

	}

}
