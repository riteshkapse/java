package queues;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class BlockingQueueExample {

	public static void main(String[] args) {
		
		BlockingQueue<String> bq = new ArrayBlockingQueue<String>(5);
		
		IntStream.range(1, 11).forEach((int count) -> {
			try {				
				//bq.put(""+count); //blocks if queue is full
				
				bq.add(""+count); //Throws IllegalStateException if the queue is full
				
				/*
				boolean status = bq.offer(""+count); //returns false if queue is full
				if(!status){
					System.out.println("Queue full for attempt "+count);
				}*/
				
				/*
				bq.offer(""+count, 1, TimeUnit.SECONDS); //Waits for 1 second if queue is full
				System.out.println(bq.size());
				*/
				
								
				//bq.take(); //blocks if queue empty
				
				bq.remove(); //Throws NoSuchElementException if queue is empty
								
				//System.out.println(bq.poll()); //Retrieve. Returns Null if queue is empty
				
				//System.out.println(bq.peek()); //Retreive, but doesn't remove (Similar to poll)
				
				//System.out.println(bq.poll(2, TimeUnit.SECONDS)); //Waits for 2 seconds seconds for items to be available
				
				
			} catch (Exception e) {				
				e.printStackTrace();
			}
		});
		
		System.out.println("Main exits!!!");
		
		
	}

}
