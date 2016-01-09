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

import org.jschropf.edu.pia.dao.PostDao;
import org.jschropf.edu.pia.dao.UserDao;
import org.jschropf.edu.pia.domain.Comment;
import org.jschropf.edu.pia.domain.Post;
import org.jschropf.edu.pia.domain.User; 

//@WebServlet(name = "Wall", urlPatterns = {"/wall"}) 
public class Wall extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
    private PostDao postDao;
	
	private UserDao userDao; 
	
	public Wall(PostDao postDao, UserDao userDao){
		this.postDao = postDao;
		this.userDao = userDao;
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
        Long personId = (Long)session.getAttribute("userId");
        Long commentLoaded = (Long)request.getAttribute("commentLoaded");
        List<Comment> comments = (List<Comment>) request.getAttribute("comments");
        if(comments != null)
        	System.out.println("Comments for post on wall loaded");
        Long postId = (Long) request.getAttribute("commentActive");
        if(postId != null)
        	System.out.println("Post with comments on the wall identified: "+postId);
//        if(personId == null) {
//            response.sendRedirect("login");
//            return;
//        }

        Long ownerId = personId;
        if (request.getParameter("ownerId") != null) {
            ownerId = Long.parseLong(request.getParameter("ownerId"));
        }
        List<Post> posts = null;
	if(request.getParameter("filter") != null && request.getParameter("filter").equals("popular")){	
		System.out.println("Writing top ten on wall");
		posts = postDao.topTenFor(ownerId);
	} else {
		System.out.println("Writing all on wall");
		posts = postDao.wallFor(ownerId);
	}
        request.setAttribute("posts", posts);
	// sending this so we can use it in links
        request.setAttribute("wallOwnerId", ownerId);
    	request.setAttribute("comments", comments);
    	if(request.getParameter("FriendError") != null){	
    		System.out.println("Not Friend of owner " + ownerId);
    		request.setAttribute("FriendError", request.getAttribute("FriendError"));
    	}
    	
    	List<User> friends = null;
	    String orderParam = request.getParameter("order");
	    boolean order = (orderParam == null || !orderParam.equals("DESC"));
	    String orderBy = request.getParameter("orderBy");
	    if(orderBy != null && orderBy.equals("dateOfBirth")) {
		friends = userDao.friendsSortedByDateOfBirth(personId, order);
	    } else {
		friends = userDao.friendsSortedByName(personId, order);
	    }
	    List<User> nonFriends = userDao.nonFriendsFor(personId);
	    
	    request.setAttribute("people", friends);
	    
    	ctx.getRequestDispatcher("/Wall.jsp").forward(request, response);
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
