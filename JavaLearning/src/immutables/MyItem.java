package immutables;

import java.io.Serializable;

public class MyItem implements Cloneable, Serializable{
	private String name;
	private int price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		super.clone();
		
		//Deep Copy
		MyItem clone = new MyItem();
		clone.setName(this.name);
		clone.setPrice(this.price);
		
		return clone;
	}
	
	@Override
	public String toString() {		
		return "{ name:"+this.name+", price:"+this.price+" }";
	}

}
