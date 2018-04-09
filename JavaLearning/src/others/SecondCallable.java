package others;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class SecondCallable {
		
	public static void main(String[] args){
		
		ExecutorService es = Executors.newFixedThreadPool(2);
		
		IntStream.range(1, 10).forEach((int count) -> {
			Future<Boolean> status = es.submit(()->{
				System.out.println("Running callable..." + Thread.currentThread().getName());
				return true;
			});
			
			try {
				//Blocks
				System.out.println("Done?"+status.get());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Submiting next...");
			
		});
		
		es.shutdown();
		
	}

}
