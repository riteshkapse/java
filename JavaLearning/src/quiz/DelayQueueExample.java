package quiz;
import java.security.Timestamp;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


class DelayedItem implements Delayed {
	
	private String str;
	private long delayMillis; //used internally
	private long expiryMillis;

	public DelayedItem(String str, long millis) {
		this.str = str;
		this.delayMillis = millis;
		this.expiryMillis = System.currentTimeMillis() + millis;
	}
	
	@Override
	public long getDelay(TimeUnit unit) {
		long delay = expiryMillis - System.currentTimeMillis();
		return unit.convert(delay, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public String toString() {		
		long secs = TimeUnit.SECONDS.convert(delayMillis, TimeUnit.MILLISECONDS);
		return str + " with delay seconds: "+secs;
	}


	@Override
	public int compareTo(Delayed o) {		
		return str.compareTo(((DelayedItem)o).getStr());
	}
	
	public String getStr(){
		return str;
	}
};

public class DelayQueueExample {

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Starting...");
		
		DelayQueue<Delayed> delayQueue = new DelayQueue<>();
		
		
		System.out.println("Start time: "+ new Date());
		delayQueue.put(new DelayedItem("Ten Seconds", 10000)); //not blocking as queue is unbounded
		delayQueue.put(new DelayedItem("Twenty Seconds", 20000));
		delayQueue.put(new DelayedItem("Thirty Seconds", 30000));
			
		
		System.out.println(delayQueue.take()); //blocking
		System.out.println("Take 1 time: "+ new Date());
		System.out.println(delayQueue.take()); //blocking
		System.out.println("Take 2 time: "+ new Date());
		System.out.println(delayQueue.take()); //blocking
		System.out.println("Take 3 time: "+ new Date());
	}

}
