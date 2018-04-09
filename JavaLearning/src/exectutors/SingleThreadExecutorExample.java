package exectutors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class SingleThreadExecutorExample {

	public static void main(String[] args) {
		
		Map<String, String> map = new HashMap<String,String>();
		Map<String, String> threadMap = Collections.synchronizedMap(map);
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		
		IntStream.range(1, 11).forEach( (int count) -> {
			
			es.submit( () -> {
				
				if(threadMap.containsKey(Thread.currentThread().getName())){
					String counts = threadMap.get(Thread.currentThread().getName());
					counts = counts + ", "+count;		
					threadMap.put(Thread.currentThread().getName(), counts);
				}else{
					threadMap.put(Thread.currentThread().getName(), ""+count);
				}
				
			});
			
		});
		
		
		threadMap.forEach((String key, String value) -> {
			System.out.println(key+" executed counts: "+value);
		});
		System.out.println("====>Total treads used to count \"100\": "+threadMap.size());

	}

}
