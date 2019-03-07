package cleaning;

import java.util.Comparator;

public class MyComparator implements Comparator<Word> {

	@Override
	public int compare(Word word1, Word word2) {
		
		return word2.getNbrOccurence() - word1.getNbrOccurence();
	}

}
