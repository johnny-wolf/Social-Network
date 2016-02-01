package org.jschropf.edu.pia.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Servlet for handling all users page. Prepares list of all users
 * 
 * @author Jan Schropfer
 *
 */
@WebServlet("/allUsers") 
public class AllUsers extends AbstractServlet{
	private static final long serialVersionUID = 1L;

	private UserManager userManager;

	@Autowired
	public void setAllUsers(UserManager userManager){
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
		HttpSession session = request.getSession();
        Long personId = (Long)session.getAttribute("userId");
		
        try {
			List<User> people = null;
			String orderParam = request.getParameter("order");
			boolean order = (orderParam == null || !orderParam.equals("DESC"));
			String orderBy = request.getParameter("orderBy");
			
			if(orderBy != null && orderBy.equals("dateOfBirth")) {
				people = userManager.findAllSortedByDateOfBirth(order);
			} 
			else {
				people = userManager.findAllSortedByName(order);
			}
			
	    	List<User> friends = null;
		    
		    if(orderBy != null && orderBy.equals("dateOfBirth")) {
		    	friends = userManager.friendsSortedByDateOfBirth(personId, order);
		    } 
		    else {
		    	friends = userManager.friendsSortedByName(personId, order);
		    }
		    
		    request.setAttribute("people", friends);
			
			request.setAttribute("allPeople", people);
			request.setAttribute("wallOwnerId", personId);
			
			request.getRequestDispatcher("/allPeople.jsp").forward(request, response);
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
		return "Servlet for handling all users page. Prepares list of all users";
	}
}