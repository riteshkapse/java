package completeablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CompleteableFutureExample {

	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		
		CompletableFuture<String> completableFuture = new CompletableFuture<>();
		
		es.submit(()->{
			//Sleep for some time
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Completing completableFuture !");
			completableFuture.complete("Future 1");	//Complete after 5 seconds
		});		
		
		
		System.out.println("Completing completedStage !");
		CompletionStage<String> completedStage = CompletableFuture.completedFuture("Already completed!");	
		
		completableFuture.acceptEither(completedStage , (str)->{
			System.out.println("acceptEither:"+str);
		}).thenApply((s) -> {				
				return s + "thenApply";
		}).thenRun(()->{
			System.out.println("Done");
		});

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		es.shutdown();
		es.awaitTermination(10, TimeUnit.SECONDS);
		System.out.println("-----------------------------------> Main Terminates!!!");
		

		
	}

}
