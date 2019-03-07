package cleaning;

import java.util.Comparator;

public class PageRankComparator implements Comparator<Page> {

	@Override
	public int compare(Page page1, Page page2) {
		
		return (int) (( page2.getPagerank() - page1.getPagerank() )*10000);
	}

}
