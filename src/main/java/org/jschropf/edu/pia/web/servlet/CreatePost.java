package org.jschropf.edu.pia.web.servlet;

import org.jschropf.edu.pia.ApplicationContext;
import org.jschropf.edu.pia.dao.FriendRequestDao;
import org.jschropf.edu.pia.dao.PostDao;
import org.jschropf.edu.pia.dao.UserDao;
import org.jschropf.edu.pia.dao.UserDaoJpa;
import org.jschropf.edu.pia.domain.Post;
import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.manager.PostManager;
import org.jschropf.edu.pia.manager.UserManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

//@WebServlet(name = "CreatePost", urlPatterns = {"/createPost"})
public class CreatePost extends HttpServlet {	
	@EJB
    private UserDao userDao;
	@EJB
    private PostDao postDao;
	@EJB
    private FriendRequestDao friendRequestDao;
	@EJB
	private UserManager userManager;
	@EJB
	private PostManager postManager;
	
	public CreatePost(PostManager postManager, FriendRequestDao friendRequestDao, PostDao postDao){
		this.postManager = postManager;
		this.friendRequestDao = friendRequestDao;
		this.postDao = postDao;
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
        Long ownerId = (Long)request.getAttribute("wallOwnerId");
        String username = (String)session.getAttribute("user");
        //System.out.println("searching for user: "+username);
        Long personId = (Long)session.getAttribute("userId");   
        try {
        	boolean isMultipart = ServletFileUpload.isMultipartContent(request);     
            //if (isMultipart) {
                // Create a factory for disk-based file items
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
                
                /*System.out.println("Loading Picture");
                FileItem picture = items.get(2);
                
                if(picture !=null){
                String pictureFilename = null;
                if(!picture.getName().equals("")) {
                    pictureFilename = "/a" + (new Random()).nextLong() + picture.getName();
                }
                
                File uploadedFile = new File("docroot/" + pictureFilename);
                picture.write(uploadedFile);
                }*/
	
                //String link = items.get(4).getString();
                
                System.out.println("Loading poster Id: "+items.get(3).getString("UTF-8"));
                if (items.get(3).getString() != null && !items.get(3).getString("UTF-8").equals("null")) {
                	System.out.println("Parsing id to long");
                    ownerId = Long.parseLong(items.get(3).getString("UTF-8"));
                } else{
                	System.out.println("Poster is owner of the wall");
                    ownerId = personId;
                }
                
                if(ownerId.equals(personId)){
                	System.out.println("Poster is owner of the wall");
                    ownerId = personId;
                }
                
            //}
        if(null == session.getAttribute("user")) {
        	System.out.println("No user logged in!");
            request.setAttribute("ownerId", ownerId);
        	response.sendRedirect("wall?ownerId=" + ownerId + "&FriendError=noFriend");
        	return;
        }        
		if(!personId.equals(ownerId) && !friendRequestDao.areFriends(personId, ownerId)) {
			response.sendRedirect("wall?ownerId=" + ownerId + "&FriendError=noFriend");
		    return;
		}
            
			System.out.println("releasing post");
			Date date = new Date();
			Post temp = postDao.createPost(title, text, personId, ownerId);
            postManager.releasePost(temp, personId);
            //temp = postDao.findByTexts(text, title);
            //postDao.updatePostId(personId, temp.getId());
                response.sendRedirect("wall?ownerId=" + ownerId);
            
        } catch (Exception e) {
            //handle the exception here
            System.out.println("error: "+e);
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