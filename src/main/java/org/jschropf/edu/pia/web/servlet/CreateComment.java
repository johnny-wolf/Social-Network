package org.jschropf.edu.pia.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.jschropf.edu.pia.domain.CommentValidationException;
import org.jschropf.edu.pia.domain.PostValidationException;
import org.jschropf.edu.pia.manager.CommentManager; 

public class CreateComment extends HttpServlet{
	@EJB
    private CommentDao commentDao;
	
	@EJB
	private CommentManager commentManager;

	public CreateComment(CommentDao commentDao, CommentManager commentManager){
		this.commentDao = commentDao;
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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        Long personId = (Long)session.getAttribute("userId");
        ServletContext ctx = getServletConfig().getServletContext();
        if(personId == null) {
            response.sendRedirect("login");
            return;
        }
        PrintWriter out = response.getWriter();
        try {
            String text = request.getParameter("text");
            Long postId = Long.parseLong(request.getParameter("postId"));
            Comment temp = commentDao.createComment(text, personId, postId);
            try {
				commentManager.releaseComment(temp, personId);
			} catch (CommentValidationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
                response.sendRedirect("comments?postId=" + postId);
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateCommentServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateCommentServlet at " + request.getContextPath () + "</h1>");
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

