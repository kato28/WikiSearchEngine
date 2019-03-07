package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cleaning.*;

/**
 * Servlet implementation class ServletRequest
 */
@WebServlet("/ServletRequest")
public class ServletRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
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
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		String searchRequest = request.getParameter("search");
		out.println("Search request: "+searchRequest);
		
		SearchRequest sr = new SearchRequest();
		response.setContentType("text/html");
		response.setCharacterEncoding( "UTF-8" );
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"utf-8\" />");
		out.println("<title>Search Wikipedia pages</title>");
		out.println("</head>");
		out.println("<body>");
		try {
			
			String titleUrl = null;
			String title = null;
			for(Page p: sr.findPages(searchRequest, getServletContext().getRealPath("/hamzawords.txt"))) {
				titleUrl = sr.titlePageWithIdToURL(p.getId(), getServletContext().getRealPath("/hashmap.txt"));
				title = sr.titlePageWithId(p.getId(), getServletContext().getRealPath("/hashmap.txt"));
				
				String a = "<p><a href=\""+titleUrl+"\">"+title+"</a></p>";
				out.println(a);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		out.println("</body>");
		out.println("</html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
