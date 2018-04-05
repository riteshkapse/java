package exectutors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CachedThreadPoolExecutorExample {

	public static void main(String[] args) throws InterruptedException {
		
		
		
		Map<String, String> map = new HashMap<String,String>();
		Map<String, String> threadMap = Collections.synchronizedMap(map);		
				
		
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
			System.out.println(key+" exectured counts: "+value);
		});
		System.out.println("====>Total treads used to count \"100\": "+threadMap.size());
		

	}

}
