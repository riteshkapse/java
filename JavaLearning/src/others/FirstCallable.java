package others;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

class MyCallable implements Callable<Boolean>{

	@Override
	public Boolean call() throws Exception {
		System.out.println("I am done...");
		return true;
	}
	
}

public class FirstCallable {

	public static void main(String[] args) {
		
		MyCallable callable = new MyCallable();
		
		ExecutorService es = Executors.newFixedThreadPool(2);
		
		IntStream.range(1, 11).forEach((int count)-> {
			Future<Boolean> done = es.submit(callable);
			if(done.isDone()){
				System.out.println("Above done fast (immidiate)");
			}
		});
		
		es.shutdown();

	}

}
