package quiz;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;




public class TestSemaphores {
	
	
	

	public static void main(String[] args) throws Exception{
		
		Semaphore s= new Semaphore(10);
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		IntStream.range(0, 100).forEach((int indx)->{
			
			
			try {
				s.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			es.submit(()->{
				System.out.println("Executing thread:"+Thread.currentThread().getId());
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {					
					e.printStackTrace();
				}
			});
			
			
			
			try {
				s.release();
			} catch (Exception e) {				
				e.printStackTrace();
			}
			
			
		});

		es.shutdown();
		es.awaitTermination(10, TimeUnit.MINUTES);
	}

}
