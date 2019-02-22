package immutables;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ImmutableTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		
		MyItem item = new MyItem();
		item.setName("Test 1");
		item.setPrice(1);
		
		MyImmutable immutable = new MyImmutable(1, item);
		
		item.setName("Test 2"); //Doesn't change name value
		//immutable.set <-- No setters provided		
		String name = item.getName(); name = "Test 2"; //String is immutable, means Name is immutable
		
		System.out.println(immutable);
		
		//Getters
		immutable.getMyitem().setName("Name 2");
		
		System.out.println(immutable);
		
		System.out.println("Hashcode: "+immutable.hashCode());
		
		//Serialization
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("MyImmutable.ser"));
		oos.writeObject(immutable);
		System.out.println("Object serialized...");
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("MyImmutable.ser"));
		MyImmutable immutable2 = (MyImmutable) ois.readObject();
		System.out.println(immutable2);
		System.out.println("Hashcode: "+immutable2.hashCode());
		
		
		
		

	}

}
