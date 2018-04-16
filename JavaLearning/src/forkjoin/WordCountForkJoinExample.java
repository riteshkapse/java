package forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * This example will calculate no of words in file using java 7 fork join framework
 * @author user
 *
 */

class WordCount extends RecursiveTask<Integer>{
	
	String str;

	public WordCount(String str) {
		this.str = str;
	}
	
	@Override
	protected Integer compute() {
		
		if(str.length() < 100){
			return countWords(str);
		}
		
		//Big task - time to fork
		String sub1 = str.substring(0, str.length()/2);
		String sub2 = str.substring(str.length()/2, str.length()-1);
		WordCount wc1 = new WordCount(sub1);
		WordCount wc2 = new WordCount(sub2);
		
		
		wc1.fork();
		wc2.fork();
				
		int size = wc1.join() + wc2.join();
		
		return size;
	}

	private Integer countWords(String str2) {
		return str.split(" ").length;		
	}
	
}

public class WordCountForkJoinExample {

	public static void main(String[] args) {	
		
		String testString = "The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick br 101 own fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over t 201 he lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox 301 jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps ov 401 er the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog ";
		
		ForkJoinPool pool = new ForkJoinPool();		
		System.out.println(pool.invoke(new WordCount(testString)));
		pool.shutdown();

	}

}
