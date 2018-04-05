package exectutors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class FixedThreadPoolCPUIntensiveTasks {
	
	public static void main(String[] args) throws InterruptedException{
		
		Map<String, String> map = new HashMap<String,String>();
		Map<String, String> threadMap = Collections.synchronizedMap(map);
		
		
		int nThreads = Runtime.getRuntime().availableProcessors();
		ExecutorService es = Executors.newFixedThreadPool(nThreads); 
		System.out.println("Total available processors: "+nThreads);
		
		IntStream.range(1,  101).forEach((int count) -> {
			es.submit(() -> {
				String thName = Thread.currentThread().getName();
				if(threadMap.containsKey(thName)){
					threadMap.put(thName, threadMap.get(thName) +", "+count);
				}else{
					threadMap.put(thName, ""+count);
				}
				
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			});
		});
		
		es.shutdown();
		es.awaitTermination(1, TimeUnit.MINUTES);
		
		
		threadMap.forEach((String key, String value) -> {
			System.out.println(key+" executed counts: "+value);
		});
		System.out.println("====>Total treads used to count \"100\": "+threadMap.size());
	}

}
