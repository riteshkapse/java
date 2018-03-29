package concurrency;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class AllExecutors {

	public static void main(String[] args) throws InterruptedException {
		
		
		
		Map<String, String> map = new HashMap<String,String>();
		Map<String, String> threadMap = Collections.synchronizedMap(map);		
		
		/*
		//Fixed thread pool
		ExecutorService es = Executors.newFixedThreadPool(10);
		IntStream.range(1, 101).forEach((int val) -> {
			es.submit(() -> {
				//System.out.println("Running thread for count:"+val+" with thread:"+Thread.currentThread().getName());
				if(threadMap.containsKey(Thread.currentThread().getName())){
					String counts = threadMap.get(Thread.currentThread().getName());
					counts = counts + ", "+val;		
					threadMap.put(Thread.currentThread().getName(), counts);
				}else{
					threadMap.put(Thread.currentThread().getName(), ""+val);
				}
				
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			});
		});
		*/
		
		//Cached thread pool
		ExecutorService es = Executors.newCachedThreadPool();
		IntStream.range(1, 101).forEach((int val) -> {
			es.submit(() -> {
				if(threadMap.containsKey(Thread.currentThread().getName())){
					String counts = threadMap.get(Thread.currentThread().getName());
					counts = counts + ", "+val;		
					threadMap.put(Thread.currentThread().getName(), counts);
				}else{
					threadMap.put(Thread.currentThread().getName(), ""+val);
				}
				
				try {
					TimeUnit.MILLISECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			});
		});
		
		
		es.shutdown();
		es.awaitTermination(10, TimeUnit.SECONDS);
		
		threadMap.forEach((String key, String value) -> {
			System.out.println(key+" "+value);
		});
		

	}

}
