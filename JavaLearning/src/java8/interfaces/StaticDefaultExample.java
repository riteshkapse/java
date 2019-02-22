package java8.interfaces;

public interface StaticDefaultExample {
	

	static void TestStatic(){
		System.out.println("Hello Static!");
	}
	
	
	default void TestDefault1(){
		System.out.println("Hello Detault 1!");
	}
	
	default void TestDefault2(){
		System.out.println("Hello Default 2!");
	}
	
	
	void sampleMethod();
}
