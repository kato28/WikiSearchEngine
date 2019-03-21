package main;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Optimizer {
	private static final String SORTVALUE = "linkedMap.txt";
	private static final int DICTMAXSIZE = 10000;

	static HashMap<String, Word> dictionnary = new HashMap<>();
	static LinkedHashMap<String, Word> sortedByValue;
	static ArrayList<Word> words;

	public static String deleteStopWords(String text,List<String> stopWords){

		String[] txtWords = text.split(" ");
		String result= "";

		for(int i=0;i<txtWords.length;i++) {

			if(!stopWords.contains(txtWords[i].toLowerCase()) && txtWords[i]!="") {
				if(i==txtWords.length-1) {
					result+=txtWords[i];
				}
				else {
					result+=txtWords[i]+" ";
				}

			}
		}
		return result;
	}

	public static String replaceOddChar(String s) {

		if(s.toLowerCase().contains("é"))
			s=s.toLowerCase().replace('é', 'e');
		if(s.toLowerCase().contains("è")) 
			s=s.toLowerCase().replace("è", "e");
		if(s.toLowerCase().contains("à")) 
			s=s.toLowerCase().replace("à", "a");
		if(s.toLowerCase().contains("ê"))
			s=s.toLowerCase().replace("ê", "e");
		if(s.toLowerCase().contains("ç")) 
			s=s.toLowerCase().replace("ç", "c");
		if(s.toLowerCase().contains("'"))
			s=s.toLowerCase().replace("'", " ");
		if(s.toLowerCase().contains("â"))
			s=s.toLowerCase().replace("â", "a");
		if(s.toLowerCase().contains("ù"))
			s=s.toLowerCase().replace("ù", "u");
		if(s.toLowerCase().contains("ô"))
			s=s.toLowerCase().replace("ô", "o");
		if(s.toLowerCase().contains("î"))
			s=s.toLowerCase().replace("î", "i");
		if(s.toLowerCase().contains("û"))
			s=s.toLowerCase().replace("û", "u");

		return s;
	}

	public static void printMostOccurences() {
		
		List<Word> mapValues = new ArrayList<Word>(dictionnary.values());
		
		// sorting HashMap by values using comparator 
		Collections.sort(mapValues,new MyComparator());
		sortedByValue = new LinkedHashMap<String, Word>();

		// copying entries from List to LinkedHashmap
		for(int i =0; i<mapValues.size(); i++) {
			sortedByValue.put(mapValues.get(i).getWord(), mapValues.get(i));
		}
		try {
			SaveData.writeSortedMap(sortedByValue, SORTVALUE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void findOccurences(String text,Page id ){

		String[] txtWords =text.split(" ");
		Word test; 
		for(int i=0;i<txtWords.length;i++) {

			test=dictionnary.get(txtWords[i]);
			if(!txtWords[i].isEmpty()) {

				if(test!=null) {
					dictionnary.replace(txtWords[i],test, new Word(test.getNbrOccurence()+1,test.getPages(),id,txtWords[i]));

				}
				else {
					if(dictionnary.size()<DICTMAXSIZE)
						dictionnary.put(txtWords[i],new Word(1,id,txtWords[i]));
				}
			}
		}
	}

	public static ArrayList<Word> getWOrds(){
		// Words sorted in nbOccurences in LinkedHashmap
		words = new ArrayList<>(sortedByValue.values());
		return words;
	}
}
