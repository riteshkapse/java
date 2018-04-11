package others;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class SpliteratorExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		List<String> lst = new ArrayList<String>();
		
		IntStream.range(1, 1001).forEach((int i)->{
			lst.add(""+i);
		});
		
		Spliterator<String> itr = lst.spliterator();		
		//System.out.println(itr.estimateSize());
		
		/*
		 * trySplit will return frontal split e.g. {1 2 3 4} of {1 2 3 4 5 6 7 8}
		 * Last items woulb be kept in original spliterator
		 */
		Spliterator<String> split1 = itr.trySplit();
		//System.out.println(itr.estimateSize());
		//System.out.println(split1.estimateSize());		
		
		Spliterator<String> split2 = itr.trySplit();
		//System.out.println(itr.estimateSize());
		//System.out.println(split1.estimateSize());
		//System.out.println(split2.estimateSize());
		
		ExecutorService es = Executors.newFixedThreadPool(3);
		
		Future<String> str1 = es.submit(()->{
			StringBuilder str = new StringBuilder();
			split1.forEachRemaining((String elmt)->{
				str.append(elmt);			
			});
			return str.toString();
		});	
		
		Future<String> str2 = es.submit(() ->{
			StringBuilder str = new StringBuilder();
			split2.forEachRemaining((String elmt)->{
				str.append(elmt);
			});
			return str.toString();
		});	
		
		Future<String> str3 = es.submit(() -> {
			StringBuilder str = new StringBuilder();
			itr.forEachRemaining((String elmt)->{
				str.append(elmt);
			});
			return str.toString();
		});
				
		//get() call blocks until completion
		String result = str1.get() + str2.get() + str3.get();
		
		System.out.println("Result:"+result);

		es.shutdown();
	}

}
