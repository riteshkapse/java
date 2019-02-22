package forkjoin;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class ItemSearch extends RecursiveTask<Integer> {
	
	private static final int THRESHOLD = 4;
	
	List<String> catalog;
	String item;
	Integer resultCount = 0;
	
	

	ItemSearch(List<String> catalog, String item){
		this.catalog = catalog;
		this.item = item;
	}
	
	@Override
	protected Integer compute() {
		
		//Fork Join
		if(catalog.size() > 4){
			ItemSearch[] forks = splitTask(catalog, item, catalog.size());
			for(ItemSearch fork: forks){
				resultCount = resultCount + fork.join();
				System.out.println("found in fork...");
			}
		}else{//Compute result	
			
			catalog.forEach((String elmt) -> {
				if(elmt.equals(item)){
					resultCount++;
					System.out.println("found in main...");
				}
			} );	
		}
		
		return resultCount;
	}

	/*
	 * Splits the list into two for multi-core processing
	 */
	private ItemSearch[] splitTask(List<String> catalog, String item, int size) {
		System.out.println("=====> fork at "+size /2);
		int mid = size / 2; 
		ItemSearch split1 = new ItemSearch(catalog.subList(0, mid), item); 
		ItemSearch split2 = new ItemSearch(catalog.subList(mid, size), item);
		split1.fork();
		split2.fork();
		return new ItemSearch[]{split1, split2};
	}
	
}




public class ForkJoinItemSearch {

	public static void main(String[] args) {
		
		String[] catalog = {"aa", "bb", "cc","aa","aa", "bb", "cc","aa"};
		
		ForkJoinPool pool = new ForkJoinPool();
		int result = pool.invoke(new ItemSearch(Arrays.asList(catalog), "aa"));
		pool.shutdown();
		System.out.println("Found matches:" + result);
		

	}

}
