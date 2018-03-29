package quiz;

import java.util.concurrent.atomic.AtomicInteger;

class MyObject implements Runnable {
	
	static AtomicInteger count = new AtomicInteger(0);
	static Object lock = new Object();

	public void t1(){
		System.out.println("Excecuting T1 - "+Thread.currentThread().getName());
	}
	
	public void t2(){
		System.out.println("Excecuting T2 - "+Thread.currentThread().getName());
	}
	
	@Override
	public void run() {
		
		this.t1();
		count.incrementAndGet();
		
		if (count.get() < 50) {				
			try {
				synchronized (lock) {
					lock.wait();
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(count.get() == 50){
			synchronized (lock) {
			lock.notifyAll();	
			}
		}
		
		this.t2();		
		
	}
	
}

public class SequenceMethod {

	public static void main(String[] args) throws InterruptedException {
		
		
		
	}

}
