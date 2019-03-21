package main;

import java.util.Comparator;

public class PageRankComparator implements Comparator<Page> {

	@Override
	public int compare(Page page1, Page page2) {
		
		if(page1.getPagerank() == page2.getPagerank()) {
			return 0;
		}
		else if(page1.getPagerank() < page2.getPagerank()){
			//return (int) (( page2.getPagerank() - page1.getPagerank() )*100000000);
			return -1;
		}
		else {
			return 1;
		}
	}

}
