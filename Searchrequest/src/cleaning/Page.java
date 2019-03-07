package cleaning;

import java.io.Serializable;

public class Page implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private double pagerank;

	public Page(Integer id) {
		this.id = id;
		pagerank=0.0;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public double getPagerank() {
		return pagerank;
	}
	public void setPagerank(double pagerank) {
		this.pagerank = pagerank;
	}
	@Override
	public String toString() {
		return "Page [id=" + id + ", pagerank=" + pagerank + "]";
	}
	
}
