package quiz;

import java.util.ArrayList;
import java.util.List;

/*
 * This is basic implementation of producer consumer problem
 */


enum TYPE{
	PRODUCER,
	CONSUMER
};

/*
 * Worker could be Producer or Consumer. Thease can be in multiple numbers.
 */
class Worker implements Runnable{
	
	static String item =  null;
	
	TYPE type;
	
	public Worker(TYPE type) {
		this.type = type;
	}
	
	public void run(){
		
		if(this.type == TYPE.PRODUCER){			
			//Producer stuff
			for(int i =0; i<10; i++){
				if(item == null){
					item = "Item "+i;
					System.out.println(Thread.currentThread().getName()+" producing... "+i);
				}else{//Item is not consumed yet
					try {
						Thread.sleep(100);
						i--;
					} catch (InterruptedException e) {						
						e.printStackTrace();
					}
				}
			}			
		}else{			
			//Consumer stuff
			for(int i =0; i<5; i++){
				if(item != null){
					System.out.println(Thread.currentThread().getName()+" consuming... "+item);
					item = null;
				}else{//Item is not produced
					try {
						Thread.sleep(20);
						i--;
					} catch (InterruptedException e) {						
						e.printStackTrace();
					}
				}				
			}			
		}
		
	}
}


public class ProducerConsumerProblem {

	public static void main(String[] args) {
		
		//Producer		
		for(int i = 0; i<10; i++){
			Thread producer = new Thread(new Worker(TYPE.PRODUCER));
			producer.start();
		}
		
		//Consumer
		for(int i=0; i<10; i++){
			Thread consumer = new Thread(new Worker(TYPE.CONSUMER));
			consumer.start();
		}
		
		System.out.println("Main exits!!!");

	}

}
