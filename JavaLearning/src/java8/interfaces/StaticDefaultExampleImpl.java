package java8.interfaces;

public class StaticDefaultExampleImpl implements StaticDefaultExample {
	
	@Override
	public void sampleMethod() {
		System.out.println("Sample Method in Impl!");		
	}
	
	
	
	public static void main(String[] args) {
		StaticDefaultExample impl  = new StaticDefaultExampleImpl();
		
		StaticDefaultExample.TestStatic();
		impl.TestDefault1();
		impl.TestDefault2();
		impl.sampleMethod();

	}



}
