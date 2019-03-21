package Tst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import main.SaveData;
import main.Word;

public class DictionnaryTest {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		LinkedHashMap<String, Word> dictionnary = SaveData.getSortedMap("linkedMap.txt");
		ArrayList<Word> words = new ArrayList<>(dictionnary.values());
		System.out.println(words.size());
		for(int i=0;i<1000;i++) {
			System.out.print(words.get(i).getWord()+" | ");
		}
		
	}

}
