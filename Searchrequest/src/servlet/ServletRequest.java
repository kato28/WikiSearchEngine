package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.Page;

/**
 * Servlet implementation class ServletRequest
 */
@WebServlet("/ServletRequest")
public class ServletRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @throws Exception 
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletRequest() {
		super();
		// TODO Auto-generated constructor stub

	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ArrayList<EjbResult> results= new ArrayList<>();
		ArrayList<Page> pages= new ArrayList<>();
		int nOfPages;
		String searchRequest = request.getParameter("search");
		int currentPage = Integer.valueOf(request.getParameter("currentPage"));
		int recordsPerPage = Integer.valueOf(request.getParameter("recordsPerPage"));

		try {
			if(ContextListener.request.equals(searchRequest)) {
				pages = ContextListener.getPages(currentPage, (recordsPerPage+currentPage) );
			}
			else if(!ContextListener.request.equals(searchRequest)){
				pages = updateResearch(searchRequest, currentPage, recordsPerPage);
			}
			if(pages.size()==0) {
				results.add(new EjbResult("Oops..Sorry.. Your input is not in our dictionnary","http://localhost:8080/Searchrequest/home"));
				ContextListener.setList(pages);
				ContextListener.request="";
				
			}
			else {
				uploadResult(results, pages);
			}

			nOfPages = ContextListener.results.size() / recordsPerPage;

			if (nOfPages % recordsPerPage > 0) {
				nOfPages++;
			}

			System.out.println("number of result : "+ContextListener.results.size());
			setParametre(request, searchRequest, currentPage, recordsPerPage, results, nOfPages);
			this.getServletContext().getRequestDispatcher("/WEB-INF/Result.jsp").forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	private ArrayList<Page> updateResearch(String searchRequest, int currentPage, int recordsPerPage) throws Exception {
		ArrayList<Page> pages;
		pages = ContextListener.sr.findPages(searchRequest);
		ContextListener.request=searchRequest;
		ContextListener.setList(pages);
		pages = ContextListener.getPages(currentPage, (recordsPerPage+currentPage));
		return pages;
	}


	private void setParametre(HttpServletRequest request, String searchRequest, int currentPage, int recordsPerPage,
			ArrayList<EjbResult> results, int nOfPages) {
		request.setAttribute("noOfPages", nOfPages);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("recordsPerPage", recordsPerPage);
		request.setAttribute("results", results);
		request.setAttribute("search", searchRequest);
	}


	private void uploadResult(ArrayList<EjbResult> results, ArrayList<Page> pages)
			throws ClassNotFoundException, IOException {
		String titleUrl;
		String title;
		String pageRank;
		for(Page p: pages) {
			titleUrl = ContextListener.sr.titlePageWithIdToURL(p.getId());
			title = ContextListener.sr.titlePageWithId(p.getId());
			pageRank = ""+p.getPagerank();
			results.add(new EjbResult(title, titleUrl,pageRank));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
