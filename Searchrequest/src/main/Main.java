package main;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

public class Main {

	public static void main(String[] args) throws XMLStreamException, IOException, ClassNotFoundException {
		// show the progress of your program
		Counter counter = new Counter(false, 250000);
		Progress progress = new Progress(counter);
		progress.start();

		ParseFile parsing = new ParseFile();
		//path to your file wikipedia.xml 
		//parsing.parseFile("/home/dardouri/Documents/MAAIN/frwiki.xml",counter);
		
		System.out.println("Parse file finished, parsing output begins");
		//name of your outpuut 
		ParseOutput.parseOutput("/home/dardouri/outputs/finalOutput/output.xml",counter);

	}

}
