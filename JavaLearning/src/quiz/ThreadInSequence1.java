package quiz;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class ThreadInSequence1 {
	
	
	
	public static void main(String[] args) throws InterruptedException {
		
		
		
		
		/*
		
		//Create single thread executor and submit runnables in sequence
		 
		ExecutorService es = Executors.newSingleThreadExecutor();
		
		es.submit(() -> {
			System.out.println("Executing 1...");
		});
		
		es.submit(() -> {
			System.out.println("Executing 2...");
		});
		
		es.submit(() ->{
			System.out.println("Executing 3...");
		});
		
		es.shutdown();
		es.awaitTermination(10, TimeUnit.SECONDS);
		*/
		
		
		
		
		
		/*
		
		//Wait for submited runnbale to finish(done) befor submitting next runnable
		
		ExecutorService es = Executors.newFixedThreadPool(3);		
		
		
		Runnable r1 = () -> {			
			try {				
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}			
			System.out.println("Executing 1...");			
		};
		Future<?> t1 = es.submit(r1);
		
		
		Runnable r2 = () -> {	
			while(!t1.isDone()){
				//do nothing
			}
			try {				
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("Executing 2...");			
		};
		Future<?> t2 = es.submit(r2);
		
		
		Runnable r3 = () -> {	
			while(!t2.isDone()){
				//do nothing
			}
			try {				
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
			System.out.println("Executing 3...");			
		};
		
		Future<?> t3 = es.submit(r3);
		
		System.out.println("Main thread finishes...");
		
		es.shutdown();
		es.awaitTermination(10, TimeUnit.SECONDS);	
		
		*/
		
		
		
		
		/*
		//Using thread join
		Thread t1 = new Thread( () -> {
			try {				
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Runing T1");
		});
		t1.start();
		
		Thread t2 = new Thread( () -> {
			try {	
				t1.join();
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Runing T2");
		});
		t2.start();
		
		Thread t3 = new Thread( () -> {
			try {
				t2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Runing T3");
		});
		t3.start();
		
		//Below will block main thread
		//t1.start();
		//t1.join();
		//t2.start();
		//t2.join();
		//t3.start();
		
		System.out.println("Comleting main....!");
		*/
		
		
		
		/*
		//Using wait, notify
		Thread t1 = new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Runing T1");
		});
		t1.start();	
		

		Thread t2 = new Thread(() -> {
			try {
				synchronized(t1){
					t1.wait(); //wait for t1 to finish
				}				
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Runing T2");
		});
		t2.start();
		

		Thread t3 = new Thread(() -> {
			try {
				synchronized(t2){
					t2.wait(); //wait for t2 to finish
				}				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Runing T3");
		});	
		t3.start();

		System.out.println("Completing main....!"); */
		
		
		
		
		/*
		//Using CompletableFuture
		CompletableFuture<String> f1 = new CompletableFuture<String>();
		Thread t1 = new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Runing T1");
			f1.complete("Done");
		});
		t1.start();	
		
		
		
		CompletableFuture<String> f2 = new CompletableFuture<String>();
		Thread t2 = new Thread(() -> {
			try {
				f1.get(); //Blocks
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException |  ExecutionException e) {
				e.printStackTrace();
			}
			System.out.println("Runing T2");
			f2.complete("Done");
		});
		t2.start();
		
		
		
		Thread t3 = new Thread(() -> {	
			try {
				f2.get(); //Blocks
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			System.out.println("Runing T3");
		});	
		t3.start();
		
		System.out.println("Completing main....!"); 
		*/
		
		
		
		//Using CompletableFuture
		CompletableFuture<String> f1 = new CompletableFuture<String>();		
		ExecutorService es = Executors.newFixedThreadPool(10);
		
		f1.thenRunAsync(() -> { 
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Runing T1 "+ Thread.currentThread());
		}, es).thenRunAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Runing T2 "+ Thread.currentThread());
		}, es).thenRunAsync(() -> {			
			System.out.println("Runing T3 "+ Thread.currentThread());
			es.shutdown(); // Done with my work
		}, es);
		
		f1.complete("Go");
				
		System.out.println("Completing main.... ! "+ Thread.currentThread());		
		
	}
	
	
	
	

}
