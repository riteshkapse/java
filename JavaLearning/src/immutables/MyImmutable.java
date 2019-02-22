package immutables;

import java.io.Serializable;

public final class MyImmutable implements Serializable{
	
	final private int id;
	final private MyItem item;
	
	public MyImmutable(int id, MyItem item) {
		this.id = id;
		try {
			item = (MyItem) item.clone(); //Item now holds reference to clone object
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Could not clone MyItem instance");
		}
		this.item = item;
	}	
	
	@Override
	public String toString() {		
		return "{ id: "+this.id+", item: "+this.item+" }";
	}
	
	public MyItem getMyitem(){
		try {
			return (MyItem) this.item.clone();
		} catch (CloneNotSupportedException e) {			
			throw new RuntimeException("Could not clone MyItem instance");
		}		
	}
}
