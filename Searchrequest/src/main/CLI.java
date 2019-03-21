package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.PageRank;

public class CLI implements PageRank {

	private static final int K = 1000;
	private final  double DAMPINGFACTOR = 0.150;
	private final  double EPS = 0.00000001;

	private static List<String> stopwords = new ArrayList<String>();
	private List<Double> C = new ArrayList<>();
	private List<Integer> L = new ArrayList<>();
	private List<Integer> I = new ArrayList<>();
	private List<Double> pagerank = new ArrayList<>();
	private List<Double> pagerank2 = new ArrayList<>();
	private List<Integer> randoms = new ArrayList<>();

	private String sw;
	private String title[];
	private int id2;
	private double value;

	public CLI() throws IOException {
		L.add(0);
		stopwords = ParseFile.getStopWords();
	}

	public void Cli(String text, HashMap<String, Integer> storedTitleIdMap) {
		//Array contains outbound links(pages) for each single page
		ArrayList<Integer> outboundPages= new ArrayList<>(); 
		while(text.contains("[[")){

			text = text.substring(text.indexOf("[[")+2);
			if(text.contains("]]")) {
				sw = text.substring(0, text.indexOf("]]"));
			} else {
				break;
			}

			if(sw.contains("|")) {
				getLink(storedTitleIdMap, outboundPages);			
			}
			else {
				if(storedTitleIdMap.containsKey(sw)) {
					id2=storedTitleIdMap.get(sw);
					outboundPages.add(id2);
				}	
			}
		}
		if(!outboundPages.isEmpty()){
			//Example value= 5 pages coming out of one single page
			value= (double) 1/outboundPages.size();
			updateCLI(outboundPages);
		}
		else {
			L.add(L.get(L.size()-1));
		}

	}

	private void getLink(HashMap<String, Integer> storedTitleIdMap, ArrayList<Integer> outboundPages) {
		title=sw.split("\\|");
		if(title.length!=0) {
			if(storedTitleIdMap.containsKey(title[0])) {
				//Get the id of the page out of hashmap
				id2=storedTitleIdMap.get(title[0]); 
				outboundPages.add(id2); 
			}	
		}
	}

	private void updateCLI(ArrayList<Integer> outboundPages) {
		for(int i=0;i<outboundPages.size();i++) { //
			C.add(value);
			//get the id(ie it's number) of the pagenumber
			I.add(outboundPages.get(i)); 

		}
		L.add(L.get(L.size()-1)+outboundPages.size());
	}

	/* (non-Javadoc)
	 * @see cleaning.PageRank#calculPageRank()
	 */
	@Override
	public void calculPageRank() {
		boolean exit = false;
		double init;
		init = (double) 1/(L.size()-1);
		initRandoms();
		initPageRank(init);
		while (!exit) {
			calculMatrix();
			//pagerank.clear();
			updatePageRank();
			//Stop condition
			exit = verifyStopCondition(exit);
			pagerank.clear();
			updatePageRank();
			initPageRank2();
		}
	}

	private void initPageRank2() {
		for(int i=0;i<pagerank2.size();i++) {
			pagerank2.set(i, 0.0);
		}
	}

	private void updatePageRank() {
		for(int k=0;k<pagerank2.size();k++) {
			pagerank.add(pagerank2.get(k));
		}
	}

	private void calculMatrix() {
		for (int i = 0; i < L.size()-1; i++) {
			if(L.get(i) != L.get(i+1)) {
				calculIteration(i);
			}
			else {
				calculRandomIteration(i);
			}
			
		}
		calculDampingFactory();
	}

	private void calculDampingFactory() {
		for(int j=0;j<pagerank2.size();j++) {
			pagerank2.set(j, (1 - DAMPINGFACTOR) * pagerank2.get(j) +(DAMPINGFACTOR/(L.size()-1)));
		}
	}

	private boolean verifyStopCondition(boolean exit) {
		for (int i = 0; i < pagerank.size(); i++) {
			if (Math.abs(pagerank.get(i)-pagerank2.get(i))< this.EPS) {
				exit=true;
				break;
			}
		}
		return exit;
	}

	private void initPageRank(double init) {
		for (int i = 0; i < L.size()-1; i++) {
			pagerank.add(i, init);
			pagerank2.add(i, 0.0);
		}
	}

	private void calculRandomIteration(int i) {
		int[] indexs = randomIndexs();
		double a;
		double b;
		
		for( int j=0;j<indexs.length;j++) {
			a= pagerank.get(i)/K;
			b= pagerank2.get(indexs[j])+a;
			pagerank2.set(indexs[j], b);
		}
	}

	private void calculIteration(int i) {
		double a;
		for (int j = L.get(i); j < L.get(i + 1); j++) {

			a =  C.get(j) * pagerank.get(i);
			pagerank2.set(I.get(j),a + pagerank2.get(I.get(j)));
		}
	}

	public List<Double> getPagerank() {
		return pagerank;
	}

	private int[] randomIndexs() {
		int [] result = new int[K];
		Collections.shuffle(randoms);
		for(int i=0;i<K;i++) {
			result[i] = randoms.get(i);
		}
		
		return result;
	}

	private void initRandoms() {
		for(int i=0;i<L.size()-1;i++) {
			randoms.add(i);
		}
	}
	

	public static HashMap<String, Integer> fixHashmapTitles (HashMap<String, Integer> storedTitleIdMap) {
		HashMap<String, Integer> hash = new HashMap<>();

		for(Map.Entry<String, Integer> entry : storedTitleIdMap.entrySet()) {
			String key = entry.getKey();
			key = Optimizer.deleteStopWords(key, stopwords);
			key = Optimizer.replaceOddChar(key);
			key = key.toLowerCase();
			hash.put(key, entry.getValue());
		}
		return hash;
	}

	public void printSize(){
		System.out.println("[C= "+C.size()+", L= "+L.size()+", I = "+I.size()+"]");
	}

	public void printC() {
		System.out.println(C);
	}

	public void printI() {
		System.out.println(I);
	}

	public void printL() {
		System.out.println(L);
	}

	public List<Double> getC() {
		return C;
	}

	public void setC(ArrayList<Double> c) {
		C = c;
	}

	public List<Integer> getL() {
		return L;
	}

	public void setL(ArrayList<Integer> l) {
		L = l;
	}

	public List<Integer> getI() {
		return I;
	}

	public void setI(ArrayList<Integer> i) {
		I = i;
	}
}
