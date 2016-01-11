package org.jschropf.edu.pia.web.servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Servlet handling user profile editation.
 *
 * Date: 26.11.15
 *
 * @author Jan Schropfer
 */
@WebServlet("/editProfile") 
public class ProfileEdit extends AbstractServlet {
	private static final long serialVersionUID = 1L;

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

    @Autowired
    public void setProfileEdit(UserManager userManager) {
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
    	HttpSession session = req.getSession();
    	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy",Locale.ENGLISH);

		try {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(MAX_MEMORY_SIZE);
                factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
                String uploadFolder = getServletContext().getRealPath("") + File.separator + DATA_DIRECTORY;
                String username = (String)session.getAttribute("user");
                Long personId = (Long)session.getAttribute("userId");
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(MAX_REQUEST_SIZE);
                
                List<FileItem> items = upload.parseRequest(req);
                
                for (FileItem i : items) {
					System.out.println(i.toString());
                }

                String password = items.get(0).getString("UTF-8");
                System.out.println("Loaded pass: "+password);
                String confirmPwd = items.get(1).getString("UTF-8");
                System.out.println("Loaded cpass: "+confirmPwd);
                String fname = items.get(2).getString("UTF-8");
                System.out.println("Loaded first name: "+fname);
                String lname = items.get(3).getString("UTF-8");
                System.out.println("Loaded last name: "+lname);
                String birthdate = items.get(4).getString("UTF-8");
                System.out.println("Loaded birthdate: "+birthdate);
                
                User result = userManager.findByUsername(username);
                if((password != null && !password.isEmpty()) || (confirmPwd != null && !confirmPwd.isEmpty())){
                	if(!Objects.equals(password, confirmPwd)) {
                		errorDispatch("The password and confirm password fields do not match!", req, resp);
                		return;
                	}
                	else
                		userManager.updatePassword(password, result);
                }

 	
                System.out.println(username+" "+password+" "+fname+" "+lname+" "+birthdate);
        	
               	String [] parts = birthdate.split(Pattern.quote("."));
               	System.out.println(parts.length);
               	if(!(parts.length < 3) || !(parts.length > 3)){
                	if(!(Integer.getInteger(parts[0]) > 31 || Integer.getInteger(parts[0]) < 1) || !(Integer.getInteger(parts[1]) < 1 || Integer.getInteger(parts[1]) > 12) 
    					|| Integer.getInteger(parts[2]) < 1900 ||Integer.getInteger(parts[2]) > 2016){
                		Date bday = format.parse(birthdate);
                		userManager.updatePersonalInformation(personId, fname, lname, bday);
                	}
                }
                else
                	userManager.updatePersonalInformation(personId, fname, lname, null, null, null);
            
                //Picture uploading
                String fileName = new File(items.get(5).getName()).getName();
                System.out.println("Setting the file name: " +fileName );
                String filePath = uploadFolder + File.separator + result.getUsername() + "/" + fileName;
                
                if(fileName != "" || !fileName.isEmpty()){	
                	File uploadedFile = new File(filePath);
                	System.out.println(filePath);
                	String newName = randomString(10, fileName);
                	uploadedFile = new File(uploadFolder + File.separator + newName);
                	System.out.println(uploadFolder + File.separator + newName);
                	items.get(5).write(uploadedFile);
                	userManager.updatePicture(result, newName); 
                }

                req.setAttribute("person", result);
                req.setAttribute("fname", result.getfName());
                req.setAttribute("lname", result.getlName());
                req.setAttribute("birthdate", result.getBirthDate());
                req.setAttribute("picture", result.getPicture());
                req.getRequestDispatcher("userProfile").forward(req, resp);  //not perfect, user should get a message registration was successful!
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
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
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