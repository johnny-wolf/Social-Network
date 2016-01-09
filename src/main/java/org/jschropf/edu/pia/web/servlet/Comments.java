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

public class Comments extends AbstractServlet{
	private static final long serialVersionUID = 1L;
    
    @EJB
    private CommentManager commentManager;

    public Comments(CommentManager commentManager){
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
        HttpSession session = request.getSession(true);
        Long personId = (Long)session.getAttribute("personId");
        Long ownerId = (Long)Long.parseLong(request.getParameter("wallOwnerId"));
//        if(personId == null) {
//            response.sendRedirect("login");
//            return;
//        }
        Long postId = Long.parseLong(request.getParameter("postId"));  
        System.out.println("Loading comments for post: "+postId);
        List<Comment> comments = commentManager.commentsFor(postId);
        request.setAttribute("comments", comments);
        request.setAttribute("commentActive", postId);
        request.setAttribute("wallOwnerId", ownerId);
        System.out.println("Redirecting back to wall of: "+ownerId);
        ctx.getRequestDispatcher("/wall?ownerId="+ownerId).forward(request, response);
        
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CommentServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CommentServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
    }// </editor-fold>
}
