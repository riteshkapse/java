package quiz;

/**
 * 
 * @author Ritesh Kapse
 * 
 * Simple thread working on same object one by one. One thread executing at one time.
 *
 */
class MyObject2 {
	
	public void M1(){
		System.out.println("M1 got executed!");
	}
	
	public void M2(){
		System.out.println("M2 got executed!");
	}
	
	public void M3(){
		System.out.println("M3 got executed!");
	}
}


public class SequenceMethod1 {

	public static void main(String[] args) throws InterruptedException {
		
		MyObject2 obj = new MyObject2();
		
		Thread t1 = new Thread (()-> obj.M1());
		Thread t2 = new Thread (() -> obj.M2());
		Thread t3 = new Thread(() -> obj.M3());
		
		t1.start();
		t1.join();
		
		t2.start();
		t2.join();
		
		t3.start();
		t3.join();

	}

}
