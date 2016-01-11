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
import org.jschropf.edu.pia.domain.FriendRequest;
import org.jschropf.edu.pia.manager.FriendRequestManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Servlet for handling creation of friend request
 * 
 * @author Jan Schropfer
 *
 */
@WebServlet("/friendRequest") 
public class CreateFriendRequest extends AbstractServlet{
	private static final long serialVersionUID = 1L;

	private FriendRequestManager friendRequestManager;
	
	@Autowired
	public void setCreateFriendRequest(FriendRequestManager friendRequestManager){
		//this.friendRequestDao = friendRequestDao;
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
        request.setCharacterEncoding("UTF-8"); 
        HttpSession session = request.getSession();
        Long personId = (Long)session.getAttribute("userId");
        
        try {
        	
        	if(personId == null){
        		request.setAttribute("err", "To add friend, you must be logged in!");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
        	}
        	
        	Long targetId = Long.parseLong(request.getParameter("targetId").trim());
        	System.out.println("retrieving target id: " + targetId + " and user id: " + personId);
             
            
            if(!friendRequestManager.areUnanswered(personId, targetId)){
            	System.out.println("Requesting friendship - Starting");
            	friendRequestManager.releaseFriendRequest(personId, targetId);
                System.out.println("Requesting friendship - Done");
                request.getRequestDispatcher("wall?ownerId="+targetId).forward(request, response);
            }
            else {
                request.setAttribute("err", "friend request failed. you already requested this person or you do not have rights to request this person.");
                request.getRequestDispatcher("wall?ownerId="+targetId).forward(request, response);
            }
            
        }catch(Exception e){ 
        	System.out.println("Exception " + e);
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
        return "Servlet for handling creation of friend request";
    }
}
