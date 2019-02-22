package quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;



public class ProducerConsumerSemaphoreExample {
	
	private static List<Integer> items = new ArrayList<>();
	static Semaphore  permit = new Semaphore(1);	
	

	public static void main(String[] args) {
		
		ExecutorService es =  Executors.newFixedThreadPool(10);		
				
		IntStream.range(1, 101).forEach((int count)->{
			
			//Producers
			es.submit(()->{
				
				while(true){ //un-ending process
					
					if(permit.tryAcquire()){					
						items.add(ThreadLocalRandom.current().nextInt());
						System.out.println("Produced new item. Current stock:"+items);
						permit.release();
						TimeUnit.MICROSECONDS.sleep(50); //Give consumer thread chance
					}else{
						//Wait for some time, consumer might be busy
						TimeUnit.MILLISECONDS.sleep(10);
					}
					
				}
				
			});
			
			
			//Consumer
			es.submit(()->{
				
				while(true){ //un-ending process
					
					if(permit.tryAcquire()){
						
						//System.out.println("acquired..."+items.size());
						if(items.size()>0){
							Integer removedItem = items.remove(items.size()-1);						
							System.out.println("Consumed new item:"+removedItem+". Current stock:"+items);	
						}
						permit.release();
						
					}else{
						//Wait for sometime, producer might be busy
						TimeUnit.MILLISECONDS.sleep(10);
					}
					
				}
				
			});
						
			
		});		
				
		es.shutdown(); //Main completes	
	}

}
