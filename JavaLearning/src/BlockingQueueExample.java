import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class BlockingQueueExample {

	public static void main(String[] args) {
		
		BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);
		
		//blockingQueue.add("Make queue full..."); //throws java.lang.IlligalStateException
		
		IntStream.range(0, 10).forEach((int index)->{
			System.out.println("Running thread:"+Thread.currentThread().getId());
			blockingQueue.add("add throws exception if blockingQueue if full");
			blockingQueue.remove(); //Remove throws exception if blockiingQueue is emptry
		});
		
		//blockingQueue.remove(); //removing what? throws java.util.NoSuchElementException
		System.out.println(blockingQueue.offer("Add 1")); // return true if added successfully
		System.out.println(blockingQueue.offer("Add 2"));
		System.out.println(blockingQueue.offer("Add 3"));
		System.out.println(blockingQueue.offer("Add 4"));
		
		System.out.println("\n");
		
		System.out.println(blockingQueue.peek()); //Doesn't remove item - returns Object
		
		System.out.println("\n");
		
		System.out.println(blockingQueue.poll()); // return Object if sucessfully removed
		System.out.println(blockingQueue.poll());
		System.out.println(blockingQueue.poll());
		System.out.println(blockingQueue.poll());
	
		System.out.println("\n");
		
		IntStream.range(0, 10).forEach((int index)->{
			System.out.println("Running thread:"+Thread.currentThread().getId());
			try {
				blockingQueue.put("add throws exception if blockingQueue if full");//Put is blocking operation
				blockingQueue.take(); //take is blocking operation
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
		});		
		try {
			blockingQueue.take(); //This blocks!!!
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}

}
