package org.jschropf.edu.pia.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jschropf.edu.pia.dao.UserDao;
import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired; 

/**
 * Servlet for preparing list of users who requested friendship for friendrequests.jsp
 * 
 * @author Jan Schropfer
 *
 */
@WebServlet("/friendRequests") 
public class FriendRequests extends AbstractServlet{
	private static final long serialVersionUID = 1L;

    private UserManager userManager;
	
    @Autowired
	public void setFriendRequests(UserManager userManager){
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
        Long personId = (Long)session.getAttribute("userId");
           
        List<User> unansweredFriendRequests = userManager.unansweredFriendRequestsFor(personId);
	    
    	List<User> friends = null;
	    String orderParam = request.getParameter("order");
	    boolean order = (orderParam == null || !orderParam.equals("DESC"));
	    String orderBy = request.getParameter("orderBy");
	    
	    if(orderBy != null && orderBy.equals("dateOfBirth")) {
	    	friends = userManager.friendsSortedByDateOfBirth(personId, order);
	    } 
	    else {
	    	friends = userManager.friendsSortedByName(personId, order);
	    }
	    
	    List<User> nonFriends = userManager.nonFriendsFor(personId);
	    request.setAttribute("people", friends);
        
        request.setAttribute("wallOwnerId", personId);
        request.setAttribute("friendRequests", unansweredFriendRequests);
	    request.getRequestDispatcher("/friendRequests.jsp").forward(request, response);
	    
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
        return "Servlet for preparing list of users who requested friendship for friendrequests.jsp";
    }
} 

