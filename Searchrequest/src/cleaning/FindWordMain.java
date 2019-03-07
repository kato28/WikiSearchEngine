package cleaning;

public class FindWordMain {

	public static void main(String[] args) {
		SearchRequest sr = new SearchRequest();
		
		try {
			System.out.println("web ville");
			for(Page p: sr.findPages("web ville", "hamzaWords.txt")) {
				System.out.println(sr.titlePageWithIdToURL(p.getId(), "hashmap.txt")+" | "+p.getPagerank());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
