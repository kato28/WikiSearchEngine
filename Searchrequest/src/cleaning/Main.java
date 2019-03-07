package cleaning;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

public class Main {

	public static void main(String[] args) throws XMLStreamException, IOException, ClassNotFoundException {
	
	    Counter counter = new Counter(false,200000);
		Progress progress = new Progress(counter);
		progress.start();

		//ParseFile parsing = new ParseFile();
		//parsing.parseFile("/home/dardouri/Documents/MAAIN/frwiki.xml",counter);
		//System.out.println("Parse file finished, parsing output begins");
		ParseOutput.parseOutput("output.xml",counter);

	}

}
