package interfaces;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import main.Counter;

public interface Parsing {

	void parseFile(String fileName, Counter counter) throws XMLStreamException, IOException;

}