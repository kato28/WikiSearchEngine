package main;


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

import interfaces.Parsing;

public class ParseFile implements Parsing {
	private static final String OUTPUTNAME = "output.xml";
	public static final String TITLEIDMAP ="hashmap.txt";
	public static final String STOPWORDS ="stopwords-fr.txt";
	public static final String ELEMRACINE ="mediawiki";

	private static final int MAXPAGESIZE = 250000;
	private HashMap<String, Integer> titleIDMap = new HashMap<>();
	private final String PAGE = "page";
	private final String TITLE = "title";
	private final String TEXT = "text";
	private final String NOTESREF = "Notes et références";
	private boolean bPage=false, bTitle=false;
	private String title = "";
	private String content = "";
	private XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
	private XMLEventFactory eventFactory = XMLEventFactory.newInstance();
	private XMLEventWriter eventWriter;
	private XMLEvent end = eventFactory.createDTD("\n");

	private String[] categoryWords = {"compilateur", "informatique", "algorithme", "base de données", "java","javascript","hostmask","DNS",
			"développement informatique", "python", "android", "intelligence artificielle", "IPv6","IPv4","commutateur réseau",	
			"programmation embarquée","antivirus", "linux", "logiciel libre","Active Directory","Active Directory","darknet","compilateur",
			"client-serveur","processeur","framework","application web","adresse MAC","adresse IP","administrateur réseau","base de données",
			"ethernet","architecture logicielle","application hybride","docker","java ee","langage de programmation","dhcp","programmation embarquée",
			"machine virtuelle","programmation","application mobile","hacker", "mathématiques", "théorème", "linéarité", "opérande","algèbre","adjacent", "linéaire", "arithmétique","axiome",
			"bijection","polynôme","booléen", "chasles", "cryptage","coefficient", "convergent", "corrélation", "lemme", "dénominateur", "dérivée", "diagramme","symétrie", "ensemble de définition",
			"euclidien", "exponentiel", "factorielle", "factorisation", "gauss", "géometrie", "gradient", "graphe", "heuristique", "hexadécimal", "logarithme", "matrice", "scalaire", "équation"};


	/* (non-Javadoc)
	 * @see cleaning.Parsing#parseFile(java.lang.String, cleaning.Counter)
	 */
	@Override
	public void parseFile(String fileName,Counter counter) throws XMLStreamException, IOException{

		System.setProperty("jdk.xml.totalEntitySizeLimit", String.valueOf(Integer.MAX_VALUE));

		List<String> stopwords = getStopWords();
		eventWriter= outputFactory.createXMLEventWriter(new FileOutputStream(OUTPUTNAME));

		StartDocument startDocument = eventFactory.createStartDocument();
		eventWriter.add(startDocument);
		eventWriter.add(end);

		createStartElement(ELEMRACINE);
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader streamReader = factory.createXMLStreamReader(new FileReader(fileName));

		while(streamReader.hasNext()) {
			if(counter.getID() < MAXPAGESIZE)
			{
				streamReader.next();
				if(streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
					verifyPage(streamReader);	
					verifyTitle(streamReader);
					verifyText(counter, stopwords, streamReader);
				}
			}
			else {
				break;
			}
		}
		createEndElement(ELEMRACINE);

		try {
			SaveData.writeHashmap(titleIDMap, TITLEIDMAP);
			Optimizer.printMostOccurences();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void verifyText(Counter counter, List<String> stopwords, XMLStreamReader streamReader)
			throws XMLStreamException {
		if(streamReader.getLocalName().equals(TEXT)) {
			content = streamReader.getElementText();
			for(String ss : categoryWords)
			{
				if((content.toLowerCase().contains(ss) || title.toLowerCase().contains(ss)) && bPage && bTitle) {

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

	private void verifyPage(XMLStreamReader streamReader) {
		if(streamReader.getLocalName().equals(PAGE)) {

			bPage=true;
		}
	}

	private void verifyTitle(XMLStreamReader streamReader) throws XMLStreamException {
		if(streamReader.getLocalName().equals(TITLE)) {
			//keep title as it is
			title = streamReader.getElementText();
			bTitle=true;
		}
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
		
		FileInputStream fstream = new FileInputStream(STOPWORDS);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		while ((strLine = br.readLine()) != null) {
			stopwords.add(strLine);
		}
		br.close();		
		return stopwords;
	}


}