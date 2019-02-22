package quiz;

import java.util.*;
public class HashMapTest {
	public static void main(String[] args) {
		Map<ToDos, String> m = new HashMap<ToDos, String>();
		ToDos t1 = new ToDos("Monday");
		ToDos t2 = new ToDos("Monday");
		ToDos t3 = new ToDos("Tuesday");
		m.put(t1, "Meetings");
		m.put(t2, "Pay Bills");
		m.put(t3, "Laundry");
		System.out.println(m.size());
	}
}
class ToDos {
	String day;
	ToDos(String d) {
		day = d;
	}
	public boolean equals(Object o) {
		return ((ToDos) o).day.equals(this.day);
	}
	//public int hashCode() {	return 1;}
}