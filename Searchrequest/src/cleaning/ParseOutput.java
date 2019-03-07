package cleaning;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class ParseOutput {


	final static String TEXT = "text";
	static double somme = 0.0;
	static ArrayList<Page> pagesRetrieved = new ArrayList<>();

	public static void parseOutput(String file,Counter counter) throws XMLStreamException, IOException, ClassNotFoundException {
		CLI cli = new CLI();
		String content = "";
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader = xmlInputFactory.createXMLStreamReader(new FileReader(file));
		HashMap<String, Integer> titleIDMap = new HashMap<>();
		HashMap<String,Integer> titleIDMapRetrieved=null;
		

		try {
			titleIDMap = SaveData.getHashMapData("hashmap.txt");
			titleIDMapRetrieved = CLI.fixHashmapTitles(titleIDMap);
			//System.out.println(titleIDMapRetrieved);
		} catch (Exception e) {
			e.printStackTrace();
		} 


		while (streamReader.hasNext()) {
			streamReader.next();
			if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
				if(streamReader.getLocalName().equals(TEXT)) {
					content= streamReader.getElementText();
					counter.decrement();
					cli.Cli(content.toLowerCase(), titleIDMapRetrieved);
				}

			}
		}
		cli.calculPageRank();

		for(Double d : cli.getPagerank()) {

			somme += d;

		}
		System.out.println("la somme :"+somme);
		LinkedHashMap<String,Word> sortedWords = SaveData.getSortedMap("linkedMap.txt");
		ArrayList<Word> words = new ArrayList<>(sortedWords.values());
		ArrayList<Page> pages;
		ArrayList<Double> pageranks = cli.getPagerank();
		for(int i =0;i<words.size();i++) {
			pages = words.get(i).getPages();
			for( int j=0;j<pages.size();j++) {
				pages.get(j).setPagerank(pageranks.get(pages.get(j).getId()));
			}
			words.get(i).setPages(pages);

		}
		//System.out.println("Somme du page rank: "+somme);
		SaveData<Double> saveC = new SaveData<Double>();
		SaveData<Integer> saveL = new SaveData<Integer>();
		SaveData<Integer> saveI = new SaveData<Integer>();
		SaveData<Double> pageRankSaved = new SaveData<Double>();
		//SaveData<Page> pagesSaveData = new SaveData<Page>();
		SaveData<Word> wordsHamza = new SaveData<Word>();
		System.out.println("start saving data");
		try {
			//pagesRetrieved = pagesSaveData.getArrayOfObjects("pages.txt");
			saveC.writeObject(cli.getC(), "C.txt");
			saveL.writeObject(cli.getL(), "L.txt");
			saveI.writeObject(cli.getI(), "I.txt");
			wordsHamza.writeObject(words, "hamzaWords.txt");
			pageRankSaved.writeObject(cli.getPagerank(), "pagerank.txt");


		} catch (Exception e) {

			e.printStackTrace();
		}
		/*	for(int i=0; i<cli.getPagerank().size(); i++) {
			pagesRetrieved.get(i).setPagerank(
					cli.getPagerank().get(pagesRetrieved.get(i).getId()));
		}
		System.out.println(pagesRetrieved); */

		counter.setStop(true);
	}

}
