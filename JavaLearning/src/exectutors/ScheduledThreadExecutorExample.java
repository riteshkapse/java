package exectutors;

import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ScheduledThreadExecutorExample {
	
	public static void main(String[] args) throws InterruptedException{
		
		Map<String, String> map = new HashMap<String,String>();
		Map<String, String> threadMap = Collections.synchronizedMap(map);
		
		
		ScheduledExecutorService es = Executors.newScheduledThreadPool(2);
		
		IntStream.range(1, 11).forEach((int count) -> {
			es.scheduleAtFixedRate(()->{
				
				System.out.println(LocalTime.now().getSecond()+"----------------------------------->"+Thread.currentThread().getName()+" running count:"+count);
				String thName = Thread.currentThread().getName();
				if(threadMap.containsKey(thName)){
					threadMap.put(thName, threadMap.get(thName) +", "+count);
				}else{
					threadMap.put(thName, ""+count);
				}				
				
				
			}, 0, 5, TimeUnit.SECONDS);
		});
		
		//Delay futher execution so as to allow scheduled thread to run
		TimeUnit.SECONDS.sleep(30);
		
		es.shutdown();
		
		
		threadMap.forEach((String key, String value) -> {
			System.out.println(key+" executed counts: "+value);
		});
		System.out.println("====>Total treads used to count \"100\": "+threadMap.size());
	}
}
