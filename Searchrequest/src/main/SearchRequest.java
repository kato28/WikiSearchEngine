package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import interfaces.SearchInterface;

public class SearchRequest implements SearchInterface {
	public static final String STOPWORDS ="stopwords-fr.txt";

	HashMap<String,Word> hashmap;
	ArrayList<Word> pageRankedPages;
	Map<Integer, String> mapInversed;
	List<String> stopwords; 

	public SearchRequest(String path1,String path2) throws Exception{
		// TODO Auto-generated constructor stub
		URL path = this.getClass().getResource(path1);
		URL stopWordsPath = this.getClass().getResource(STOPWORDS);
		this.stopwords = getStopWords(stopWordsPath.getFile());
		hashmap = SaveData.getSortedMap(path.getFile());
		Map<String, Integer> titleIdMap = new HashMap<>();
		path = this.getClass().getResource(path2);
		titleIdMap = SaveData.getHashMapData(path.getFile());
		mapInversed = titleIdMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
	}	

	/* (non-Javadoc)
	 * @see cleaning.SearchInterface#findPage(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Page> findPage(String word) {
		ArrayList<Page> pages=null;
		if(hashmap.containsKey(word)) {
			pages = hashmap.get(word).getPages();
			Collections.sort(pages, new PageRankComparator());
		}
		return pages;
	}
	/* (non-Javadoc)
	 * @see cleaning.SearchInterface#findPages(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Page> findPages(String requete) throws Exception{
		ArrayList<Page> pages;
		ArrayList<Integer> ids= new ArrayList<>();
		ArrayList<Page> results = new ArrayList<>();
		ArrayList<Page> wordInTitle = new ArrayList<>();
		requete = Optimizer.deleteStopWords(requete, stopwords);
		requete = Optimizer.replaceOddChar(requete);
		if(requete.contains(" ")) {

			String [] word = requete.split(" ");

			for( int i=0;i<word.length;i++) {
				pages = findPage(word[i]);
				findIntersectLinks(pages, ids, results, wordInTitle, word, i);
			}

		} else {	
			results = findPage(requete);
		}
		if(results !=null)
		Collections.sort(results, new PageRankComparator());

		if(!wordInTitle.isEmpty()) {
			ArrayList<Page> newResult = new ArrayList<>();
			for(Page p:wordInTitle) {
				newResult.add(p);
			}
			for(Page p:results) {
				newResult.add(p);
			}
			return newResult;
		}
		else if( results == null ) {
			results = new ArrayList<>();
			results.add(new Page(-1));
			return results;
		}
		else {
			return results;

		}

	}


private void findIntersectLinks(ArrayList<Page> pages, ArrayList<Integer> ids, ArrayList<Page> results,
		ArrayList<Page> wordInTitle, String[] word, int i) {
	String userinput = "";
	
	if( pages != null) {
		for(Page p: pages) {
			userinput = mapInversed.get(p.getId()).toLowerCase();
			userinput= Optimizer.replaceOddChar(userinput);
			
			if(word[i].toLowerCase().contains(userinput)) {
				wordInTitle.add(p);
			}
			ids.add(p.getId());
			if(word.length == getNbOccurence(ids, p.getId())) {
				results.add(p);

			}
		}
	}
}


/* (non-Javadoc)
 * @see cleaning.SearchInterface#getNbOccurence(java.util.ArrayList, int)
 */
@Override
public int getNbOccurence(ArrayList<Integer> list,int value) {
	int nb = 0;
	for(Integer p:list) {
		if(p == value)
			nb++;
	}
	return nb;
}

/* (non-Javadoc)
 * @see cleaning.SearchInterface#titlePageWithId(int, java.lang.String)
 */
@Override
public String titlePageWithId(int i) throws ClassNotFoundException, IOException {

	return mapInversed.get(i);

}
/* (non-Javadoc)
 * @see cleaning.SearchInterface#titlePageWithIdToURL(int, java.lang.String)
 */
@Override
public String titlePageWithIdToURL(int i) {
	return titleToURL(mapInversed.get(i));

}

/* (non-Javadoc)
 * @see cleaning.SearchInterface#titleToURL(java.lang.String)
 */
@Override
public String titleToURL(String s) {
	if(s.contains(" ")) {
		s = s.replaceAll(" ", "_");
		s = "https://fr.wikipedia.org/wiki/"+s;
	} else {
		s = "https://fr.wikipedia.org/wiki/"+s;
	}
	return s;
}
public List<String> getStopWords(String path) throws IOException{
	
	ArrayList<String> stopwords = new ArrayList<>();
	
	FileInputStream fstream = new FileInputStream(path);
	BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	String strLine;
	while ((strLine = br.readLine()) != null) {
		stopwords.add(strLine);
	}
	br.close();		
	return stopwords;
}
}
