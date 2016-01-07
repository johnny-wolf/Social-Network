package org.jschropf.edu.pia.web.servlet;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jschropf.edu.pia.ApplicationContext;
import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.web.auth.AuthenticationService;

/**
 * Servlet handling user login requests
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public class Login extends HttpServlet {

    private static final String USERNAME_PARAMETER = "username";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String OWNER_ID = "ownerId";

    private static final String ERR_ATTRIBUTE = "err";

    private AuthenticationService authService;
    
    private EntityManager em;

    public Login(AuthenticationService authService, EntityManager em) {
        this.authService = authService;
        this.em = em;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	HttpSession session = req.getSession(true);
    	resp.setContentType("text/html;charset=UTF-8");
    	req.setCharacterEncoding("UTF-8"); 
        String username = req.getParameter(USERNAME_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        System.out.println("Validating username: "+ username + "and password: " + password);
        if(username.isEmpty() || password.isEmpty()){
        	req.setAttribute(ERR_ATTRIBUTE, "Invalid credentials!");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }else{
	        boolean authenticated = authService.authenticate(req.getSession(), username, password);
	        if(authenticated) {
	        	User temp = (User)em.createNamedQuery("User.findByUserName", User.class).setParameter("username", username).getSingleResult();
	        	Long ownerId = temp.getId();
	        	System.out.println("Login user: "+username+" id: "+ownerId);
	        	session.setAttribute("userId",ownerId );
	            resp.sendRedirect("/wall?ownerId="+ownerId);
	        } else {
	            req.setAttribute(ERR_ATTRIBUTE, "Invalid credentials!");
	            req.getRequestDispatcher("/register").forward(req, resp);
	        }
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
    	doPost(request, response);
    }
}
