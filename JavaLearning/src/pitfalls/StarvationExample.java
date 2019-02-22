package pitfalls;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class StarvationExample {
	
	public static void main(String[] args) throws InterruptedException {
		IntStream.range(0, 10).forEach((int indx)->{
			Thread T = new Thread(()-> {
				System.out.println("Executing thread "+indx);
				try {
					TimeUnit.MINUTES.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
			} );
			T.setPriority(Thread.MAX_PRIORITY);
			T.start();
		});
		Thread T = new Thread(()-> {
			System.out.println("Executing thread LEAST Priority!");
		} );
		T.setPriority(Thread.MIN_PRIORITY);
		T.start();
		
		
		System.out.println("Main terminates!");
		
	}

}
