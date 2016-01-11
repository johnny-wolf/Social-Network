package org.jschropf.edu.pia.web.servlet;

import java.io.IOException;
import java.util.Calendar;

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
 * Servlet for preparing information for user profile page
 * 
 * @author Jan Schropfer
 *
 */
@WebServlet("/userProfile")
public class UserProfile extends AbstractServlet{

	private static final long serialVersionUID = 1L;
	private UserManager userManager;
	
	@Autowired
	public void setUserProfile(UserManager userManager){
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
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("user");
			
		User person = userManager.findByUsername(username);
		System.out.println("Setting information about: "+username);

		request.setAttribute("person", person);
		request.setAttribute("fname", person.getfName());
		request.setAttribute("lname", person.getlName());
		request.setAttribute("birthdate", person.getBirthDate());
		request.setAttribute("picture", person.getPicture());
		
		int age = 0;
		if(person.getBirthDate() != null){
		Calendar dob = Calendar.getInstance();
		dob.setTime(person.getBirthDate());
		Calendar today = Calendar.getInstance();
		age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR))
		age--;
		}
		
		request.setAttribute("age", age);

		request.getRequestDispatcher("/profile.jsp").forward(request, response);
		return;
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
		return "Servlet for preparing information for user profile page";
	}
}
