package interfaces;

import java.io.IOException;
import java.util.ArrayList;

import main.Page;

public interface SearchInterface {

	ArrayList<Page> findPage(String word) throws Exception;

	ArrayList<Page> findPages(String requete) throws Exception;

	int getNbOccurence(ArrayList<Integer> list, int value);

	String titlePageWithId(int i) throws ClassNotFoundException, IOException;

	String titlePageWithIdToURL(int i) throws ClassNotFoundException, IOException;

	String titleToURL(String s);

}