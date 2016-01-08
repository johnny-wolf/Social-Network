package org.jschropf.edu.pia.web.servlet;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jschropf.edu.pia.ApplicationContext;
import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.domain.UserValidationException;
import org.jschropf.edu.pia.manager.UserManager;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet handling user registration requests.
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public class Register extends HttpServlet {

    private static final String USERNAME_PARAMETER = "username";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String CONFIRM_PWD_PARAMETER = "confirmPwd";
    private static final String FIRSTNAME_PARAMETER = "fname";
    private static final String LASTNAME_PARAMETER = "lname";
    private static final String BIRTH_DATE_PARAMETER = "birthdate";
    private static final String ERROR_ATTRIBUTE = "err";
    
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DATA_DIRECTORY = "ref-material-prototype/img";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;
    static Random rnd = new Random();

    private UserManager userManager;
    private EntityManager em;

    public Register(UserManager userManager, EntityManager em) {
        this.userManager = userManager;
        this.em = em;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setContentType("text/html;charset=UTF-8");
    	req.setCharacterEncoding("UTF-8"); 
    	HttpSession session = req.getSession(true);
    	String username = req.getParameter(USERNAME_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        String confirmPwd = req.getParameter(CONFIRM_PWD_PARAMETER);
        String fname = req.getParameter(FIRSTNAME_PARAMETER);
        String lname = req.getParameter(LASTNAME_PARAMETER);
        String birthdate = req.getParameter(BIRTH_DATE_PARAMETER);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        Date bdate = null;
        if(birthdate != null || birthdate !=""){
			try {
				System.out.println("Converting date "+birthdate);
				bdate = format.parse(birthdate);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println(e1);
			}	
        }
        
		try {
			//boolean isMultipart = ServletFileUpload.isMultipartContent(req);     
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(MAX_MEMORY_SIZE);
                factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
                String uploadFolder = getServletContext().getRealPath("") + File.separator + DATA_DIRECTORY;
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(MAX_REQUEST_SIZE);
                
                List<FileItem> items = upload.parseRequest(req);
                
                for (FileItem i : items) {
					System.out.println(i.toString());
                }
                
                username = items.get(0).getString("UTF-8");
                System.out.println("Loaded username: "+username);
                password = items.get(1).getString("UTF-8");
                System.out.println("Loaded pass: "+password);
                confirmPwd = items.get(2).getString("UTF-8");
                System.out.println("Loaded cpass: "+confirmPwd);
                fname = items.get(3).getString("UTF-8");
                System.out.println("Loaded first name: "+fname);
                lname = items.get(4).getString("UTF-8");
                System.out.println("Loaded last name: "+fname);
                birthdate = items.get(5).getString("UTF-8");
                System.out.println("Loaded birthdate: "+fname);
                
                if(birthdate != null || birthdate !=""){
        			try {
        				System.out.println("Converting date "+birthdate);
        				bdate = format.parse(birthdate);
        			} catch (Exception e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        				System.out.println(e1);
        			}
            }
        if(!Objects.equals(password, confirmPwd)) {
            errorDispatch("The password and confirm password fields do not match!", req, resp);
            return;
        }
        	System.out.println(username+" "+password+" "+fname+" "+lname+" "+birthdate);
        	if(birthdate != null || birthdate !="")
        		userManager.register(new User(username, password, fname, lname, convertUtilDateToSqlDate(bdate)));
        	else
        		userManager.register(new User(username, password, fname, lname, null));
        	
        	Query query = em.createNamedQuery("User.findByUserName", User.class).setParameter("username", username);
            User result = (User)query.getSingleResult();
        	
        	String fileName = new File(items.get(6).getName()).getName();
        	System.out.println("Setting the file name: " +fileName );
    		String filePath = uploadFolder + File.separator + result.getUsername() + "/" + fileName;
    		System.out.println("Setting the file path: " + filePath);
    		if(fileName != "" || !fileName.isEmpty()){
	    		 File uploadDir = new File(uploadFolder + File.separator + result.getUsername());
	    		 System.out.println("Testing dir: " + uploadDir.toString());
	             if (!uploadDir.exists()) {
	                 uploadDir.mkdir();
	             }
	            File uploadedFile = new File(filePath);
	            System.out.println(filePath);
	            String newName = randomString(10, fileName);
	            uploadedFile = new File(uploadFolder + File.separator + newName);
	            System.out.println(uploadFolder + File.separator + newName);
	            items.get(6).write(uploadedFile);
	            userManager.updatePicture(result, newName);
	            
    		}
    		session.setAttribute("userId",result.getId() );
    		session.setAttribute("user",result.getUsername() );
            resp.sendRedirect("/Wall.jsp?ownerId="+result.getId());  //not perfect, user should get a message registration was successful!
		}catch(Exception e){
			errorDispatch(e.getMessage(), req, resp);
	    }
	}

    private void errorDispatch(String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.setAttribute(USERNAME_PARAMETER, req.getParameter(USERNAME_PARAMETER));
        req.setAttribute(FIRSTNAME_PARAMETER, req.getParameter(FIRSTNAME_PARAMETER));
        req.setAttribute(LASTNAME_PARAMETER, req.getParameter(LASTNAME_PARAMETER));
        req.setAttribute(BIRTH_DATE_PARAMETER, req.getParameter(BIRTH_DATE_PARAMETER));
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
    
    public static Date convertUtilDateToSqlDate(java.util.Date date){
        if(date != null) {
            Date sqlDate = new Date(date.getTime());
            return sqlDate;
        }
        return null;
    }

	String randomString( int len, String fileName ){
		System.out.println("Creating random name");
	   int j = fileName.length() - 1;
	   String type = "";
	   while(fileName.charAt(j) != '.'){
		   type = fileName.charAt(j) + type;
		   j--;
	   }
	   type = '.' + type;
	   System.out.println("Type of file: " + type);
	   //StringBuilder sb = new StringBuilder( len );
	   String s = "";
	   for( int i = 0; i < len; i++ ) 
	      //sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
		  s+= AB.charAt(rnd.nextInt(AB.length() - 1)); 
	   type = s + type;
	   return type;
	}
}
