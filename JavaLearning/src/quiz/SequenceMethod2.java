package quiz;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SequenceMethod2 {

	
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService es = Executors.newSingleThreadExecutor();
		
		Future<Boolean> status = es.submit(()->{
			MyObject.M1();
			return true;
		});		
		status.get(); //Block and execute
		
		status = es.submit(()->{
			MyObject.M2();
			return true;
		});		
		status.get();
		
		status = es.submit(()->{
			MyObject.M3();
			return true;
		});		
		status.get();
		
		
		es.shutdown();

	}

}
