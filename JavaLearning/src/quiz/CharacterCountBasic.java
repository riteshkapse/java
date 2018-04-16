package quiz;

import java.util.Map;
import java.util.TreeMap;

public class CharacterCountBasic {

	public static void main(String[] args) {
		
		String testStr = "asdfasdfasdfasf ";
		Map<Character, Integer> map = new TreeMap<>();
		
		for(int i=0; i<testStr.length(); i++){
			char ch = testStr.charAt(i);
			if(map.containsKey(ch)){
				int currentCount = map.get(ch);
				map.put(ch, currentCount+1);
			}else{
				map.put(ch, 1);
			}
		}		
		
		System.out.println("Collected characters: "+map);
	}

}
