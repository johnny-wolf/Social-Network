package org.jschropf.edu.pia.web.servlet;

import org.jschropf.edu.pia.manager.FriendRequestManager;
import org.jschropf.edu.pia.manager.PostManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet for handling creation of posts
 * 
 * @author Jan Schropfer
 *
 */
@WebServlet("/createPost")
public class CreatePost extends AbstractServlet {
	private static final long serialVersionUID = 1L;

	private PostManager postManager;

	private FriendRequestManager friendRequestManager;
	
	@Autowired
	public void setCreatePost(PostManager postManager, FriendRequestManager friendRequestManager){
		this.postManager = postManager;
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
        Long ownerId = (Long)request.getAttribute("wallOwnerId");
        Long personId = (Long)session.getAttribute("userId");   
        
        try {
                
        		FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                
                List<FileItem> items = upload.parseRequest(request);
                
                for (FileItem i : items) {
					System.out.println(i.toString());
				}
                
                String title = items.get(0).getString("UTF-8");
                System.out.println("Loaded title: "+title);
                String text = items.get(1).getString("UTF-8");
                System.out.println("Loaded text: "+text);
                ownerId = Long.parseLong(items.get(2).getString());
                System.out.println("Loading poster Id: "+items.get(3).getString("UTF-8"));
                if (items.get(3).getString() != null && !items.get(3).getString("UTF-8").equals("null")) {
                	System.out.println("Parsing id to long");
                    personId = Long.parseLong(items.get(3).getString("UTF-8"));
                } 
                else{
                	System.out.println("Poster is owner of the wall");
                    ownerId = personId;
                }
                
                if(ownerId.equals(personId)){
                	System.out.println("Poster is owner of the wall");
                    ownerId = personId;
                }

                if(null == session.getAttribute("user")) {
                	System.out.println("No user logged in!");
                	request.setAttribute("ownerId", ownerId);
                	response.sendRedirect("wall?ownerId=" + ownerId + "&FriendError=noFriend");
                	return;
                }        
                if(!personId.equals(ownerId) && !friendRequestManager.areFriends(personId, ownerId)) {
                	response.sendRedirect("wall?ownerId=" + ownerId + "&FriendError=noFriend");
                	return;
                }
            
                System.out.println("releasing post");
                postManager.releasePost(personId, title, text, ownerId);
                response.sendRedirect("wall?ownerId=" + ownerId);
            
        } catch (Exception e) {
            System.out.println("error: "+e);
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
        return "Servlet for handling creation of posts";
    }
} 