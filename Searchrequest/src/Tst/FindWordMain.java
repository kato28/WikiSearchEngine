package Tst;

import java.util.ArrayList;

import interfaces.SearchInterface;
import main.Page;
import main.ParseFile;
import main.SearchRequest;

public class FindWordMain {

	public static void main(String[] args) throws Exception {
		SearchInterface sr = new SearchRequest("updatedWordsPageRank.txt","hashmap.txt",ParseFile.getStopWords());
		System.out.println("start searching");
		ArrayList<Page> result = sr.findPages("code");
		try {
			System.out.println("starting");
			for(Page p: result) {
				System.out.println(sr.titlePageWithIdToURL(p.getId())+" | "+p.getPagerank());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
