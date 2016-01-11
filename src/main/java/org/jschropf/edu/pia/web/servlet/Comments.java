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

import org.jschropf.edu.pia.dao.CommentDao; 
import org.jschropf.edu.pia.domain.Comment;
import org.jschropf.edu.pia.manager.CommentManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Servlet for preparing list of comments for specific post
 * @author Pavel
 *
 */
@WebServlet("/comments") 
public class Comments extends AbstractServlet{
	private static final long serialVersionUID = 1L;
    

    private CommentManager commentManager;

    @Autowired
    public void setComments(CommentManager commentManager){
    	this.commentManager = commentManager;
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
        ServletContext ctx = getServletConfig().getServletContext();
    	response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8"); 
        HttpSession session = request.getSession();
        Long personId = (Long)session.getAttribute("personId");
        Long ownerId = (Long)Long.parseLong(request.getParameter("wallOwnerId"));
        Long postId = Long.parseLong(request.getParameter("postId"));  
        
        System.out.println("Loading comments for post: "+postId);
        List<Comment> comments = commentManager.commentsFor(postId);
        
        request.setAttribute("comments", comments);
        request.setAttribute("commentActive", postId);
        request.setAttribute("wallOwnerId", ownerId);
        
        System.out.println("Redirecting back to wall of: "+ownerId);
        request.getRequestDispatcher("wall?ownerId="+ownerId).forward(request, response);
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
        return "Servlet for preparing list of comments for specific post";
    }
}
