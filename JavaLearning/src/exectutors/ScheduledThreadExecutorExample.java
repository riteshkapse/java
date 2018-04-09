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
		
		
		ScheduledExecutorService es = Executors.newScheduledThreadPool(1);
		
		IntStream.range(1, 3).forEach((int count) -> {		
			
			/*
			 * scheduleWithFixedDelay:
			 * 
			 * 
			 */
			
			/*
			 * scheduleAtFixedRate:
			 * 
			 * Thread will execute within the "period" provided e.g. if a thread is scheduled to run with period 10sec
			 * and it takes only 4 seconds to complete its task, the next run will be after said period only i.e. 10sec.
			 * 
			 * If thread work is not finished in said "period", the next work will start immediatly after previous work end
			 */
			
			
			es.scheduleWithFixedDelay(() -> {
				System.out.println(LocalTime.now().getSecond()+"---->SRT:"+Thread.currentThread().getName()+" running count:"+count);
				String thName = Thread.currentThread().getName();
				if(threadMap.containsKey(thName)){
					threadMap.put(thName, threadMap.get(thName) +", "+count);
				}else{
					threadMap.put(thName, ""+count);
				}
				
				//Delay
				try {
					TimeUnit.SECONDS.sleep(4);
				} catch (InterruptedException e) {					
				}
				
				
				System.out.println(LocalTime.now().getSecond()+"---->END:"+Thread.currentThread().getName()+" running count:"+count);
			}, 0, 30, TimeUnit.SECONDS);
			
			
		});
		
		
		System.out.println(LocalTime.now().getSecond()+" --- Starting All!!");
		
		//Delay futher execution so as to allow scheduled thread to run
		TimeUnit.SECONDS.sleep(60);
		
		es.shutdown();
		
		
		threadMap.forEach((String key, String value) -> {
			System.out.println(key+" executed counts: "+value);
		});
		System.out.println("====>Total treads used to count \"100\": "+threadMap.size());
	}
}
