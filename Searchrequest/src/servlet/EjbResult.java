package servlet;

public class EjbResult {

	private String title;
	private String url;
	private String pageRank;
	
	
	
	public EjbResult(String title, String url) {
		super();
		this.title = title;
		this.url = url;
	}
	
	public EjbResult(String title, String url, String pageRank) {
		super();
		this.title = title;
		this.url = url;
		this.pageRank = pageRank;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPageRank() {
		return pageRank;
	}
	public void setPageRank(String pageRank) {
		this.pageRank = pageRank;
	}
	
}
