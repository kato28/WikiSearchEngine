package main;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class ParseOutput {
	private final static String TEXT = "text";
	private static double somme = 0.0;
	//private static ArrayList<Page> pagesRetrieved = new ArrayList<>();

	public static void parseOutput(String file,Counter counter) throws XMLStreamException, IOException, ClassNotFoundException {
		CLI cli = new CLI();
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader = xmlInputFactory.createXMLStreamReader(new FileReader(file));
		HashMap<String, Integer> titleIDMap = new HashMap<>();
		HashMap<String,Integer> titleIDMapRetrieved=null;
		try {
			titleIDMap = SaveData.getHashMapData("hashmap.txt");
			titleIDMapRetrieved = CLI.fixHashmapTitles(titleIDMap);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		while (streamReader.hasNext()) {
			streamReader.next();
			if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
				verifyText(counter, cli, streamReader, titleIDMapRetrieved);
			}
		}
		
		System.out.println("calcul pagerank started");
		cli.calculPageRank();
		calculSumPageRank(cli);
		System.out.println("la somme :"+somme);
		saveAllData(cli);
		updateWordsPageRank(cli);

		counter.setStop(true);
		System.out.println("finished ! ! ");
	}

	private static void saveAllData(CLI cli) {
		SaveData<Double> saveC = new SaveData<Double>();
		SaveData<Integer> saveL = new SaveData<Integer>();
		SaveData<Integer> saveI = new SaveData<Integer>();
		SaveData<Double> pageRankSaved = new SaveData<Double>();
		System.out.println("start saving data");
		try {
			//pagesRetrieved = pagesSaveData.getArrayOfObjects("pages.txt");
			saveC.writeObject(cli.getC(), "C.txt");
			saveL.writeObject(cli.getL(), "L.txt");
			saveI.writeObject(cli.getI(), "I.txt");
			pageRankSaved.writeObject(cli.getPagerank(), "pagerank.txt");


		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private static void updateWordsPageRank(CLI cli) throws IOException, ClassNotFoundException {
		LinkedHashMap<String,Word> sortedWords = SaveData.getSortedMap("linkedMap.txt");
		ArrayList<Page> pages;
		Word word;
		List<Double> pageranks = cli.getPagerank();
		
		for(Map.Entry<String, Word> entry:	sortedWords.entrySet()) {
			word = entry.getValue();
			pages = word.getPages();
			for( int j=0;j<pages.size();j++) {
				pages.get(j).setPagerank(pageranks.get(pages.get(j).getId()));
			}
			word.setPages(pages);
			entry.setValue(word);
		}
		
		SaveData.writeSortedMap(sortedWords, "updatedWordsPageRank.txt");
	}

	private static void calculSumPageRank(CLI cli) {
		for(Double d : cli.getPagerank()) {
			somme += d;
		}
	}

	private static void verifyText(Counter counter, CLI cli, XMLStreamReader streamReader,
			HashMap<String, Integer> titleIDMapRetrieved) throws XMLStreamException {
		String content="";
		if(streamReader.getLocalName().equals(TEXT)) {
			content= streamReader.getElementText();
			counter.decrement();
			cli.Cli(content.toLowerCase(), titleIDMapRetrieved);
		}
	}

}
