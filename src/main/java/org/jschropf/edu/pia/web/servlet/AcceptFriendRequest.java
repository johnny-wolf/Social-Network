package org.jschropf.edu.pia.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jschropf.edu.pia.dao.FriendRequestDao;
import org.jschropf.edu.pia.manager.FriendRequestManager;
import org.springframework.beans.factory.annotation.Autowired; 

/**
 * Servlet for accepting friend requests
 * 
 * @author Jan Schropfer
 *
 */
@WebServlet("/acceptFriendRequest") 
public class AcceptFriendRequest extends AbstractServlet{
	private static final long serialVersionUID = 1L;
    
    private FriendRequestManager friendRequestManager;

    @Autowired
    public void setAcceptFriendRequest(FriendRequestManager friendRequestManager){
    	this.friendRequestManager = friendRequestManager;
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
        
        try {
            Long sourceId = Long.parseLong(request.getParameter("sourceId")); 
        	friendRequestManager.acceptFriendRequest(sourceId, personId);
            request.setAttribute("wallOwnerId", personId);
            response.sendRedirect("wall?ownerId=" + personId);
                
            }catch(Exception e) { 
            	e.printStackTrace();
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet for accepting friend requests";
    }
}
