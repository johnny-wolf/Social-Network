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

public class CreateFriendRequest extends HttpServlet{
	@EJB
    private FriendRequestDao friendRequestDao;
	
	@EJB
	private FriendRequestManager friendRequestManager;
	
	public CreateFriendRequest(FriendRequestDao friendRequestDao, FriendRequestManager friendRequestManager){
		this.friendRequestDao = friendRequestDao;
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
    	System.out.println("Requesting friendship - Starting");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8"); 
        HttpSession session = request.getSession(true);
        Long personId = (Long)session.getAttribute("userId");
        PrintWriter out = response.getWriter();
        try {
        	if(personId == null){
        		request.setAttribute("err", "To add friend, you must be logged in!");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
        	}
        	System.out.println("retrieving target id: " + request.getParameter("targetId"));
            Long targetId = Long.parseLong(request.getParameter("targetId")); 
            if(personId != targetId){
            	FriendRequest temp = friendRequestDao.createFriendRequest(personId, targetId);
            	if (temp != null)
            		friendRequestManager.releaseFriendRequest(temp);
                out.println("friend requested");
                System.out.println("Requesting friendship - Done");
                response.sendRedirect("/wall?ownerId=" + targetId);
            }else {
                out.println("friend request failed. you already requested this person or you do not have rights to request this person.");
                request.setAttribute("err", "friend request failed. you already requested this person or you do not have rights to request this person.");
                response.sendRedirect("/wall?ownerId=" + targetId);
            }
        }catch(Exception e){ 
        	System.out.println("Exception " + e);
        }
        
        finally {            
            out.close();
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
