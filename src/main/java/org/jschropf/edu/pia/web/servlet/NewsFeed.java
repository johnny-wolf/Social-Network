package org.jschropf.edu.pia.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
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
import org.jschropf.edu.pia.manager.PostManager;
import org.jschropf.edu.pia.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired; 

/**
 * Servlet for preparing information for user wall
 * 
 * @author Jan Schropfer
 *
 */
@WebServlet("/newsFeed") 
public class NewsFeed extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	private PostManager postManager;

	private UserManager userManager;
	
	@Autowired
	public void setManagers(PostManager postManager, UserManager userManager){
		this.postManager = postManager;
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
        Long personId = (Long)request.getSession().getAttribute("userId");
        Long commentLoaded = (Long)request.getAttribute("commentLoaded");
        
        if(personId == null)
        	return;
        
        List<Comment> comments = (List<Comment>) request.getAttribute("comments");
        List<Post> posts = null;
        if(comments != null)
        	System.out.println("Comments for post on wall loaded");
        
        Long postId = (Long) request.getAttribute("commentActive");
        
        if(postId != null)
        	System.out.println("Post with comments on the wall identified: "+postId);

        Long ownerId = personId;
        
        if (request.getParameter("ownerId") != null) {
            ownerId = Long.parseLong(request.getParameter("ownerId"));
        }
        
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
	    	friends = userManager.friendsSortedByDateOfBirth(personId, order);
	    } 
	    else {
	    	friends = userManager.friendsSortedByName(personId, order);
	    }
	    
	    posts = postManager.wallFor(ownerId);
	    
	    for (User user : friends) {
			posts.addAll(postManager.wallFor(user.getId()));
		}
	    
	    Collections.sort(posts, new Comparator<Post>() {
	        public int compare(Post m1, Post m2) {
	            return m1.getDate().compareTo(m2.getDate());
	        }
	    });
	    Collections.reverse(posts);
        request.setAttribute("posts", posts);
	    request.setAttribute("people", friends);
	    request.setAttribute("filterPosts", request.getAttribute("filterPosts"));
	    request.setAttribute("postCount", posts.size());
	    
    	request.getRequestDispatcher("/newsfeed.jsp").forward(request, response);
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
        return "Servlet for preparing information for user wall";
    }
}
