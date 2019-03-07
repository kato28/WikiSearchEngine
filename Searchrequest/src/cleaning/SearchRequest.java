package cleaning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchRequest {

	public ArrayList<Page> findPage(String word, String file) throws Exception {

		ArrayList<Word> hamzaWords = new ArrayList<>();
		SaveData<Word> hamzaData = new  SaveData<>();
		hamzaWords = hamzaData.getArrayOfObjects(file);

		ArrayList<Page> pages = null;

		for(int i=0;i<hamzaWords.size();i++) {
			if(hamzaWords.get(i).getWord().equals(word)){
				pages = hamzaWords.get(i).getPages();
				Collections.sort(pages,new PageRankComparator());
				break;
			}

		}
		return pages;
	}
	public ArrayList<Page> findPages(String requete, String file) throws Exception{
		ArrayList<Page> pages;
		ArrayList<Integer> ids= new ArrayList<>();
		ArrayList<Page> results = new ArrayList<>();
		if(requete.contains(" ")) {
			String [] word = requete.split(" ");
			for( int i=0;i<word.length;i++) {
				pages = findPage(word[i], file);
				if( pages != null) {
					for(Page p: pages) {
						ids.add(p.getId());
						if(word.length == getNbOccurence(ids, p.getId())) {

							results.add(p);
						}
					}
				}

			}
		} else {
			results = findPage(requete, file);
		}
		return results;
	}


	public int getNbOccurence(ArrayList<Integer> list,int value) {
		int nb =0;
		for(Integer p:list) {
			if(p == value)
				nb++;
		}
		return nb;
	}
	
	public String titlePageWithId(int i, String file) throws ClassNotFoundException, IOException {
		Map<String, Integer> titleIdMap = new HashMap<>();
		titleIdMap = SaveData.getHashMapData(file);
		// Inverse title ID hashmap to ID title
		Map<Integer, String> mapInversed = titleIdMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

		return mapInversed.get(i);

	}
	public String titlePageWithIdToURL(int i, String file) throws ClassNotFoundException, IOException {
		Map<String, Integer> titleIdMap = new HashMap<>();
		titleIdMap = SaveData.getHashMapData(file);
		// Inverse title ID hashmap to ID title
		Map<Integer, String> mapInversed = titleIdMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

		return titleToURL(mapInversed.get(i));

	}
	
	public String titleToURL(String s) {
		if(s.contains(" ")) {
			s = s.replaceAll(" ", "_");
			s = "https://fr.wikipedia.org/wiki/"+s;
		} else {
			s = "https://fr.wikipedia.org/wiki/"+s;
		}
		return s;
	}
	
}
