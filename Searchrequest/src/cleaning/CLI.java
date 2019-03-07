package cleaning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CLI {

	private static final int K = 1000;
	private ArrayList<Double> C = new ArrayList<>();
	private ArrayList<Integer> L = new ArrayList<>();
	private ArrayList<Integer> I = new ArrayList<>();
	private ArrayList<Double> pagerank = new ArrayList<>();
	ArrayList<Double> pagerank2 = new ArrayList<>();
	static List<String> stopwords = new ArrayList<String>();

	final private double dampingFactor = 0.15;
	final private double eps = 0.000001;
	int i=0;
	String sw;
	String title[];
	Integer id2;
	double value;

	public CLI() throws IOException {
		L.add(0);
		stopwords = ParseFile.getStopWords();
	}

	public void Cli(String text, HashMap<String, Integer> storedTitleIdMap) {
		
		ArrayList<Integer> outboundPages= new ArrayList<>(); //Array contains outbound links(pages) for each single page
		while(text.contains("[[")){

			text = text.substring(text.indexOf("[[")+2);
			if(text.contains("]]")) {
				sw = text.substring(0, text.indexOf("]]"));
			} else 
			{
				break;
			}

			if(sw.contains("|")) {
				title=sw.split("\\|");
				if(title.length!=0) {
					if(storedTitleIdMap.containsKey(title[0])) {
						id2=storedTitleIdMap.get(title[0]); //Get the id of the page out of Jedis
						outboundPages.add(id2); 
					}	
				}			
			}
			else {
			
				if(storedTitleIdMap.containsKey(sw)) {
					id2=storedTitleIdMap.get(sw);
					outboundPages.add(id2);
				}	
			}
		}


		if(!outboundPages.isEmpty()){

			value= (double) 1/outboundPages.size();//Example value= 5 pages coming out of one single page
			

			for(int i=0;i<outboundPages.size();i++) { //
				C.add(value);
				I.add(outboundPages.get(i)); //get the id(ie it's number) of the pagenumber

			}
			L.add(L.get(L.size()-1)+outboundPages.size());
		}
		else {

			L.add(L.get(L.size()-1));
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

	public ArrayList<Double> getC() {
		return C;
	}

	public void setC(ArrayList<Double> c) {
		C = c;
	}

	public ArrayList<Integer> getL() {
		return L;
	}

	public void setL(ArrayList<Integer> l) {
		L = l;
	}

	public ArrayList<Integer> getI() {
		return I;
	}

	public void setI(ArrayList<Integer> i) {
		I = i;
	}

	public void calculPageRank() {

		boolean exit = false;
		double init;

		init = (double) 1/(L.size()-1);
		double a,b;

		//Initialiser à 1/N (1/ total pages ie 200K or nodes?)
		for (int i = 0; i < L.size()-1; i++) {
			pagerank.add(i, init);
			pagerank2.add(i, 0.0);


		}

		while (!exit) {

			for (int i = 0; i < L.size()-1; i++) {
				if(i != 0) {
					if(L.get(i) != L.get(i-1)) {
						
						for (int j = L.get(i); j < L.get(i + 1); j++) {
							
							a =  C.get(j) * pagerank.get(i);

							b =  (1-dampingFactor ) * ( pagerank2.get(I.get(j)) + a ) +(dampingFactor/(L.size()-1));

							pagerank2.set(I.get(j),b);
						}
					}
					else {
						for( int j=0;j<K;j++) {
							pagerank2.set(I.get((int) Math.random()*K), (double) 1/K);
						}
					}
				}
				
				else {
					
					for (int j = L.get(i); j < L.get(i + 1); j++) {
						
						a =  C.get(j) * pagerank.get(i);

						b =  (1-dampingFactor ) * ( pagerank2.get(I.get(j)) + a ) +(dampingFactor/(L.size()-1));

						pagerank2.set(I.get(j),b);
					}
				}
			}
			
			pagerank.clear();
			
			for(int k=0;k<pagerank2.size();k++) {
				pagerank.add(pagerank2.get(k));
			}

			//Stop condition

			for (int i = 0; i < L.size()-1; i++) {
				if (Math.abs(pagerank.get(i)-pagerank.get(i+1))< this.eps) {
					exit=true;
					break;
				}
			}
		}
		
	}

	public ArrayList<Double> getPagerank() {
		return pagerank;
	}


}
