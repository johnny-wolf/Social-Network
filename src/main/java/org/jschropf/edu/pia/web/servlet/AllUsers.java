package org.jschropf.edu.pia.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.manager.UserManager;

public class AllUsers extends AbstractServlet{
	private static final long serialVersionUID = 1L;

	private UserManager userManager;

	public AllUsers(UserManager userManager){
		this.userManager = userManager;
	}
	
	/** 
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8"); 
		ServletContext ctx = getServletConfig().getServletContext();
		HttpSession session = request.getSession(true);
        Long personId = (Long)session.getAttribute("userId");
		try {
			List<User> people = null;
			String orderParam = request.getParameter("order");
			boolean order = (orderParam == null || !orderParam.equals("DESC"));
			String orderBy = request.getParameter("orderBy");
			if(orderBy != null && orderBy.equals("dateOfBirth")) {
				people = userManager.findAllSortedByDateOfBirth(order);
			} else {
				people = userManager.findAllSortedByName(order);
			}
			request.setAttribute("allPeople", people);
			request.setAttribute("wallOwnerId", personId);
			//response.sendRedirect("/allPeople.jsp");
			ctx.getRequestDispatcher("/allPeople.jsp").forward(request, response);
		}catch(Exception e){
			System.out.println("error: "+e);
	    }
	}

	/** 
	 * Handles the HTTP <code>GET</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/** 
	 * Handles the HTTP <code>POST</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/** 
	 * Returns a short description of the servlet.
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}
}