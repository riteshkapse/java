package queues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class IterateQueue {
	
	public static void main(String[] args){
		
		BlockingQueue<String> bq = new ArrayBlockingQueue<>(50);
		
		IntStream.range(1, 101).forEach((int i)->{
			bq.offer("E"+i);
		});
		
		/*
		//Simple Iterator
		Iterator<String> itr = bq.iterator();
		while(itr.hasNext()){
			System.out.println(itr.next());
		}*/
		
		/*
		//Java 8: ForEach
		bq.forEach((String item) ->{
			System.out.println(item);
		});
		*/
		
		/*
		Collection<String> c = new ArrayList<String>();
		bq.drainTo(c); //Transfers to c and removes for bq
		System.out.println(c);
		*/
		
		/*
		String[] arr = bq.toArray(new String[bq.size()]);	
		int index = 0;
		for(String s: arr){
			System.out.println(arr[index++]);
		}*/
		
		Spliterator<String> spl = bq.spliterator();
		
		spl.forEachRemaining((String str) -> {
			System.out.println(str);
		});
		
		System.out.println(spl.characteristics());
		System.out.println("Original size"+spl.estimateSize());
		System.out.println(spl.hasCharacteristics(Spliterator.ORDERED));
		
		Spliterator<String> spl1 = spl.trySplit();
		System.out.println(spl.hasCharacteristics(Spliterator.ORDERED));
		System.out.println("spl size:"+spl.estimateSize());
		System.out.println("spl1 size:"+spl1.estimateSize());
		
		
		//bq.drainTo(c)
		//bq.toArray()
		//bq.spliterator()
		//stream vs iterator
		
		
				
		
		
		
		System.out.println(bq.size());
	}

}
