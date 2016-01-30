package org.jschropf.edu.pia.web.servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.domain.UserValidationException;
import org.jschropf.edu.pia.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Servlet handling user registration requests.
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
@WebServlet("/register")
public class Register extends AbstractServlet {
	private static final long serialVersionUID = 1L;

    private static final String USERNAME_PARAMETER = "username";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String CONFIRM_PWD_PARAMETER = "confirmPwd";
    private static final String FIRSTNAME_PARAMETER = "fname";
    private static final String LASTNAME_PARAMETER = "lname";
    private static final String BIRTH_DATE_PARAMETER = "birthdate";
    private static final String AGREEMENT_PARAMETER = "agreement";
    private static final String TEST_PARAMETER = "testQ";
    private static final String ERROR_ATTRIBUTE = "err";
    
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DATA_DIRECTORY = "ref-material-prototype/img";
    private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;
    static Random rnd = new Random();

    private UserManager userManager;

    @Autowired
    public void setRegister(UserManager userManager) {
        this.userManager = userManager;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	resp.setContentType("text/html;charset=UTF-8");
    	req.setCharacterEncoding("UTF-8"); 
    	String username = req.getParameter(USERNAME_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        String confirmPwd = req.getParameter(CONFIRM_PWD_PARAMETER);
        String fname = req.getParameter(FIRSTNAME_PARAMETER);
        String lname = req.getParameter(LASTNAME_PARAMETER);
        String birthdate = req.getParameter(BIRTH_DATE_PARAMETER);
        String test = req.getParameter(TEST_PARAMETER);
        String agreement = req.getParameter(AGREEMENT_PARAMETER);
        
       	HttpSession session = req.getSession();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy",Locale.ENGLISH);
        Date bdate = null;
        
        if(birthdate != null || birthdate !=""){
			try {
				System.out.println("Converting date "+birthdate);
				bdate = format.parse(birthdate);
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out.println(e1);
			}	
        }
        
		try {
			//Preparing for picture upload
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
            
            //Getting registration information
            username = items.get(0).getString("UTF-8");
            System.out.println("Loaded username: "+username);
            password = items.get(1).getString("UTF-8");
            System.out.println("Loaded pass: "+password);
            confirmPwd = items.get(2).getString("UTF-8");
            System.out.println("Loaded cpass: "+confirmPwd);
            fname = items.get(3).getString("UTF-8");
            System.out.println("Loaded first name: "+fname);
            lname = items.get(4).getString("UTF-8");
            System.out.println("Loaded last name: "+lname);
            birthdate = items.get(5).getString("UTF-8");
            System.out.println("Loaded birthdate: "+birthdate);
            test = items.get(7).getString("UTF-8");
            System.out.println("Loaded test: "+test);
            agreement = items.get(8).getString("UTF-8");
            System.out.println("Loaded agreement: "+agreement);

            //System.out.println("Loaded agreement: " + agreement);
            
            /*System.out.println(agreement + " " + test);
        	System.out.println(username+" "+password+" "+fname+" "+lname+" "+birthdate);
             */
            
            if(test == null || Integer.parseInt(test) != 4){
            	req.setAttribute("username", username);
            	req.setAttribute("fname", fname);
            	req.setAttribute("lname", lname);
            	req.setAttribute("birthdate", birthdate);
            	errorDispatch("Wrong answer ", req, resp);
            	return;
            }
            
            if(agreement == null || !agreement.equals("yes")){
            	req.setAttribute("username", username);
            	req.setAttribute("fname", fname);
            	req.setAttribute("lname", lname);
            	req.setAttribute("birthdate", birthdate);
            	errorDispatch("you must check that you have agreed on above informations ", req, resp);
            	return;
            }
            
            if(birthdate != null || birthdate !=""){
            	try {
            		System.out.println("Converting date "+birthdate);
        			bdate = format.parse(birthdate);
        		} catch (Exception e1) {
        			//e1.printStackTrace();
        			System.out.println(e1);
        		}
            }
            
            if(!Objects.equals(password, confirmPwd)) {
    			req.setAttribute("username", username);
    	        req.setAttribute("fname", fname);
    	        req.setAttribute("lname", lname);
    	        req.setAttribute("birthdate", birthdate);
            	errorDispatch("The password and confirm password fields do not match!", req, resp);
          	
    	        return;
            }
            
        	if(birthdate != null || birthdate !="")
        		userManager.register(new User(username, password, fname, lname, convertUtilDateToSqlDate(bdate)));
        	
        	User result = userManager.findByUsername(username);
        	
        	//Getting picture and handling his saving
        	String fileName = new File(items.get(6).getName()).getName();
        	System.out.println("Setting the file name: " +fileName );
    		String filePath = uploadFolder + File.separator + result.getUsername() + "/" + fileName;
    		System.out.println("Setting the file path: " + filePath);
    		
    		userManager.uploadFile(fileName, filePath, uploadFolder, result, items.get(6));
    		/*if(fileName != "" || !fileName.isEmpty()){

    			File uploadedFile = new File(filePath);
	            System.out.println(filePath);
	            String newName = randomString(10, fileName);
	            uploadedFile = new File(uploadFolder + File.separator + newName);
	            System.out.println(uploadFolder + File.separator + newName);
	            items.get(6).write(uploadedFile);
	            userManager.updatePicture(result, newName);  
    		}*/
    		
    		session.setAttribute("userId",result.getId() );
    		session.setAttribute("user",result.getUsername() );
    		
            resp.sendRedirect("wall?ownerId="+result.getId());
		}catch(Exception e){
			req.setAttribute("username", username);
	        req.setAttribute("fname", fname);
	        req.setAttribute("lname", lname);
	        req.setAttribute("birthdate", birthdate);
			errorDispatch(e.getMessage(), req, resp);
	    } 
	}

    private void errorDispatch(String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
        
        //return;
    }
    
    public static Date convertUtilDateToSqlDate(java.util.Date date){
        if(date != null) {
            Date sqlDate = new Date(date.getTime());
            return sqlDate;
        }
        return null;
    }

    /**
     * Method for generating random name for picture
     * @param len length of name
     * @param fileName filename for extension
     * @return
     */
	String randomString( int len, String fileName ){
		System.out.println("Creating random name");
		int j = fileName.length() - 1;
		String type = "";
		
		//getting extension
		while(fileName.charAt(j) != '.'){
		   type = fileName.charAt(j) + type;
		   j--;
	   }
		
	   type = '.' + type;
	   System.out.println("Type of file: " + type);
	   String s = "";
	   
	   //creating random name
	   for( int i = 0; i < len; i++ ) 
		  s+= AB.charAt(rnd.nextInt(AB.length() - 1)); 
	   type = s + type;
	   
	   return type;
	}
}
