package forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;



class DirSize extends RecursiveTask<Long>{
	
	private File file;

	public DirSize(File file) {
		this.file = file;
	}
	
	@Override
	protected Long compute() {
		
		if(!file.exists()){
			System.out.println("Error! - File doesn't exist!");
		}
		
		if(!file.isDirectory()){
			return file.length();
		}
		
		List<DirSize> taskList = new ArrayList<>();
		
		//Directory - time to fork
		for (File innerFile: file.listFiles()){
			DirSize inner = new DirSize(innerFile);
			inner.fork();
			taskList.add(inner);
			System.out.println("Forked for "+innerFile.getName());
		}
		
		Long size = 0L;
		
		for(DirSize task: taskList){
			size = size + task.join();
		}
		
		return size;
	}
	
}


public class DirSizeForkJoinExample {

	public static void main(String[] args) {
		
		ForkJoinPool pool = new ForkJoinPool();
		Long length = pool.invoke(new DirSize(new File("/home/user/git/learning/JavaLearning")));
		System.out.println("Folder size:"+length);
		pool.shutdown();
		System.out.println("Main exits!!!");

	}

}
