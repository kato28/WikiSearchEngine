package servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import interfaces.SearchInterface;
import main.Page;
import main.ParseFile;
import main.SearchRequest;

public class ContextListener implements ServletContextListener{
	public static final String WORDSPAGES ="updatedWordsPageRank.txt";
	public static final String TITLEIDMAP ="hashmap.txt";

	public static SearchInterface sr;
	public static String request;
	public static ArrayList<Page> results;
	public void contextInitialized(ServletContextEvent sce) {
		/**Put your codes inside , it will run when JBoss starts ***/
		try {
			results = new ArrayList<>();
			request="";
			sr = new SearchRequest(WORDSPAGES,TITLEIDMAP);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setList(ArrayList<Page> result) {
		results.clear();
		results = result;
	}
	
	public static ArrayList<Page> getPages(int begin,int end){
		ArrayList<Page> pages = new ArrayList<>();
		if( end < results.size()) {
			updateList(begin, end, pages);
		}else {
			updateList(begin, results.size(), pages);
		}
		return pages;
	}

	private static void updateList(int begin, int end, ArrayList<Page> pages) {
		for(int i=begin;i<end;i++) {
			pages.add(results.get(i));
		}
	}

}
