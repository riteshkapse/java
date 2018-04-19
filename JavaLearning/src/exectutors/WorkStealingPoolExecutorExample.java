package exectutors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class WorkStealingPoolExecutorExample {

	public static void main(String[] args) throws InterruptedException {
		
		Map<String, String> map = new HashMap<String,String>();
		Map<String, String> threadMap = Collections.synchronizedMap(map);
		
		
		ExecutorService es = Executors.newWorkStealingPool();
		
		IntStream.range(1, 10000).forEach((int count) -> {
						
			es.submit(()->{
				
				
				//For checking running threads 
				if(threadMap.containsKey(Thread.currentThread().getName())){
					String counts = threadMap.get(Thread.currentThread().getName());
					counts = counts + ", "+count;		
					threadMap.put(Thread.currentThread().getName(), counts);
				}else{
					threadMap.put(Thread.currentThread().getName(), ""+count);
				}
				
				System.out.println("Thread "+Thread.currentThread().getName()+" executing count "+count);
				
				//Sleep for a second
				try {
					TimeUnit.SECONDS.sleep(0);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		});
		
		es.shutdown();
		es.awaitTermination(2, TimeUnit.SECONDS);	
		
		
		threadMap.forEach((String key, String value) -> {
			System.out.println(key+" executed counts: "+value);
		});
		System.out.println("====>Total treads used to count \"10\": "+threadMap.size());
	
		

	}

}
