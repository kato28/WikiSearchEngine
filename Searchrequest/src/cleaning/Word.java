package cleaning;

import java.io.Serializable;
import java.util.ArrayList;

public class Word implements Serializable {

	private static final long serialVersionUID = 1L;
	private int nbrOccurence;
	private ArrayList<Page> pages;
	private String word;
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public Word(int nbrOccurence, ArrayList<Page> pages,Page page,String word) {
		super();
		this.nbrOccurence = nbrOccurence;
		 this.pages = new ArrayList<>();
		 this.pages=pages;
		 if(!pages.contains(page))
			 pages.add(page);
		 this.word=word;
	}
	

	
	public Word (int nbOccurence, Page page, String word) {
		this.nbrOccurence = nbOccurence;
		 this.pages = new ArrayList<>();
		 this.pages.add(page);
		 this.word=word;
	}

	public int getNbrOccurence() {
		return nbrOccurence;
	}

	public void setNbrOccurence(int nbrOccurence) {
		this.nbrOccurence = nbrOccurence;
	}

	public ArrayList<Page> getPages() {
		return pages;
	}

	public void setPages(ArrayList<Page> pages) {
		this.pages = pages;
	}
	@Override
	public String toString() {
		return " [nbrOccurence=" + nbrOccurence + ", pages=" + pages + ", word=" + word + "]";
	}

}
