package quiz;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
/*
 * All "Producing" printed first and then all "Consuming"
 */
public class YealdTest {

	public static void main(String[] args) {
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		IntStream.range(1, 11).forEach((int indx)->{
			es.submit(()->{
				System.out.println("Producing...");				
			});
			
		});
		

		IntStream.range(1, 11).forEach((int indx)->{
			
			
			es.submit(()->{
				Thread.yield();
				System.out.println("Consuming...");
			});
		});
		
		es.shutdown();

	}

}
