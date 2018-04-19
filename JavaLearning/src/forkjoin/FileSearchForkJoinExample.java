package forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/*
 * This program can search for file by name in specified directory
 */
class FolderSearch extends RecursiveTask<List>{
	
	File path;
	String keyword;
	
	public FolderSearch(File path, String keyword) {
		this.path = path;
		this.keyword = keyword;
	}

	@Override
	protected List compute() {
		
		List<ForkJoinTask<List>> forkResultList = new ArrayList<>();
		
		List<String> currentFolderResult = new ArrayList<>();
		File[] fileList = this.path.listFiles();
		for(int i=0; i< fileList.length; i++){
			//Fork for each subdirectory
			if(fileList[i].isDirectory()){
				FolderSearch fs = new FolderSearch(fileList[i], keyword);
				ForkJoinTask<List> fs_result = fs.fork();
				forkResultList.add(fs_result);
			}else if(fileList[i].getName().contains(keyword)){
				currentFolderResult.add(fileList[i].getAbsolutePath());
			}
		}
		
		//Join all collection results (if forked)
		for(ForkJoinTask<List> forkResult: forkResultList){
			currentFolderResult.addAll(forkResult.join());
		}
		
		return currentFolderResult;
	}
	
}
public class FileSearchForkJoinExample {

	public static void main(String[] args) {
		
		ForkJoinPool pool = new ForkJoinPool();
		
		//Search files with name having "Thread" word
		List result = pool.invoke(new FolderSearch(new File("/home/user/git/learning/JavaLearning"),"Thread"));
		pool.shutdown();
		
		result.forEach((path) ->{
			System.out.println(path);
		});
		

	}

}
