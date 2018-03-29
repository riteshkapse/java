package others;

import java.util.concurrent.TimeUnit;

public class SimpleThread {

	public static void main(String[] args) throws InterruptedException {
		
		Runnable rnbl = () -> {
			System.out.println("Thread started...");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Thread end...");
		};
		
		Thread th = new Thread(rnbl);
		Thread t2 = new Thread(rnbl);
		Thread t3 = new Thread(rnbl);
		th.start();
		t2.start();
		t3.start();
		//th.join();
		System.out.println("Main completes!");

	}

}
