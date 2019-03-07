package cleaning;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class ParseFile {

	private static final int MAXPAGESIZE = 200000;
	final String PAGE = "page";
	final String TITLE = "title";
	final String TEXT = "text";
	final String NOTESREF = "Notes et références";
	boolean bPage=false, bTitle=false;
	String title = "";
	String content = "";
	String[] categoryWords = {"compilateur", "informatique", "algorithme", "base de données", "java",
			"développement informatique", "python", "android", "intelligence artificielle", 
			"programmation embarquée","antivirus", "linux", "logiciel libre",
			"client-serveur","processeur","framework","application web",
			"machine virtuelle","programmation","application mobile","hacker"};
	HashMap<String, Integer> titleIDMap = new HashMap<>();

	XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
	XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	XMLEventWriter eventWriter;

	XMLEvent end = eventFactory.createDTD("\n");

	public void parseFile(String fileName,Counter counter) throws XMLStreamException, IOException{

		System.setProperty("jdk.xml.totalEntitySizeLimit", String.valueOf(Integer.MAX_VALUE));

		List<String> stopwords = getStopWords();
		eventWriter= outputFactory.createXMLEventWriter(new FileOutputStream("output.xml"));

		StartDocument startDocument = eventFactory.createStartDocument();
		eventWriter.add(startDocument);
		eventWriter.add(end);

		createStartElement("mediawiki");
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader = factory.createXMLStreamReader(new FileReader(fileName));

		while(streamReader.hasNext()) {
			if(counter.getID() < MAXPAGESIZE)
			{
				streamReader.next();

				if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT)
				{
					if(streamReader.getLocalName().equals(PAGE)) {

						bPage=true;
					}	
					if(streamReader.getLocalName().equals(TITLE)) {
						//keep title as it is
						title = streamReader.getElementText();
						bTitle=true;
					}
					if(streamReader.getLocalName().equals(TEXT)) {
						content = streamReader.getElementText();
						for(String ss : categoryWords)
						{
							if((content.contains(ss) || title.toLowerCase().contains(ss)) && bPage && bTitle) {

								createStartElement(PAGE);
								createNode(eventWriter, TITLE, title);
								bTitle=false;
								bPage=false;

								content = content.replaceAll("\n", "");

								if(content.contains(NOTESREF)){
									content = content.substring(0,content.indexOf(NOTESREF));
								}
								content = Optimizer.replaceOddChar(content);
								content = Optimizer.deleteStopWords(content, stopwords);

								titleIDMap.put(title, counter.getID());
								Optimizer.findOccurences(content,new Page(counter.getID()));

								counter.increment();
								createNode(eventWriter, TEXT, content);

								createEndElement(PAGE);
								break;
							}
						}
					}
				}
			}
			else {
				break;
			}
		}
		createEndElement("mediawiki");

		try {
			SaveData.writeHashmap(titleIDMap, "hashmap.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Optimizer.printMostOccurences();

		//System.out.println("Total number of pages : "+counter.getID());


	}

	private void createStartElement(String name) throws XMLStreamException {
		
		StartElement pageStartElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(pageStartElement);
		eventWriter.add(end);
	}
	
	private void createEndElement(String name) throws XMLStreamException {
		
		EndElement eElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(eElement);
		eventWriter.add(end);
	}

	public void createNode(XMLEventWriter eventWriter, String name,String value) throws XMLStreamException {

		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");

		// create Start node
		StartElement sElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(tab);
		eventWriter.add(sElement);

		// create Content
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);

		// create End node
		EndElement eElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(eElement);
		eventWriter.add(end);   
	}

	public static List<String> getStopWords() throws IOException{
		
		ArrayList<String> stopwords = new ArrayList<>();
		FileInputStream fstream = new FileInputStream("stopwords-fr.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null) {
			stopwords.add(strLine);
		}
		br.close();		
		return stopwords;
	}


}