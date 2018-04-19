package exectutors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ExecutorCallingMethodsExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		ExecutorService es = Executors.newFixedThreadPool(4);
		
		//Submit
		IntStream.range(1, 11).forEach((int count) -> {
			es.submit(() -> {
				System.out.println("es.submit() submits a 'Value returning task' for execution "+count+ " by thred "+Thread.currentThread().getName());
			});
		});

		TimeUnit.SECONDS.sleep(1);
		System.out.println("==============================================================");
				
		
		
		//Submit returning future
		IntStream.range(1, 11).forEach((int count) -> {
			Future<Integer> future = es.submit(() -> {
				System.out.print("es.submit() submits a 'Value returning task' for execution "+count+ " by thred "+Thread.currentThread().getName());
				return count;
			});
			
			try {
				System.out.println(". Getting future. "+future.get());
			} catch (InterruptedException e) {				
				e.printStackTrace();
			} catch (ExecutionException e) {				
				e.printStackTrace();
			}			
		});
		
		TimeUnit.SECONDS.sleep(1);
		System.out.println("==============================================================");
		
		
		
		
		//Execute
		IntStream.range(1, 11).forEach((int count) -> {
			es.execute(() -> {
				System.out.println("es.execute() executes given command at some time in future. Count: "+count+ " by thred "+Thread.currentThread().getName());
			});
		});		
		
		TimeUnit.SECONDS.sleep(1);
		System.out.println("==============================================================");
		
		
		//Invoke		
		List<Callable<Integer>> tasks = new ArrayList<>();
		IntStream.range(1, 11).forEach((int count) -> {
			tasks.add(() -> {
				System.out.println("es.invokeAny(Collection<? extends Callable>) return results of any one task. Count: "+count+ " by thred "+Thread.currentThread().getName());
				return count;
			});
		});	
		int invokeAnyResult = es.invokeAny(tasks);
		System.out.println("Invoke any result: " + invokeAnyResult);
		
		TimeUnit.SECONDS.sleep(1);
		System.out.println("==============================================================");
		
		//InvokeAll
		System.out.println("Invoking all");
		es.invokeAll(tasks);
		TimeUnit.SECONDS.sleep(1);
		System.out.println("==============================================================");
		
		
		
		
		es.shutdown();
		es.awaitTermination(10, TimeUnit.SECONDS);
	}

}
