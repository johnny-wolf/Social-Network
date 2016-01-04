package org.jschropf.edu.pia.web.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.domain.UserValidationException;
import org.jschropf.edu.pia.manager.UserManager;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

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

    private UserManager userManager;

    public Register(UserManager userManager) {
        this.userManager = userManager;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter(USERNAME_PARAMETER);
        String password = req.getParameter(PASSWORD_PARAMETER);
        String confirmPwd = req.getParameter(CONFIRM_PWD_PARAMETER);
        String fname = req.getParameter(FIRSTNAME_PARAMETER);
        String lname = req.getParameter(LASTNAME_PARAMETER);
        String birthdate = req.getParameter(BIRTH_DATE_PARAMETER);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        Date bdate = new Date(1970, 1, 1);
        if(birthdate != null || birthdate !=""){
			try {
				System.out.println("Converting date "+birthdate);
				bdate = format.parse(birthdate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        
        }
        if(!Objects.equals(password, confirmPwd)) {
            errorDispatch("The password and confirm password fields do not match!", req, resp);
            return;
        }

        try {
        	System.out.println(username+" "+password+" "+fname+" "+lname+" "+birthdate);
        	if(birthdate != null || birthdate !="")
        		userManager.register(new User(username, password, fname, lname, convertUtilDateToSqlDate(bdate)));
        	else
        		userManager.register(new User(username, password, fname, lname, null));
            resp.sendRedirect("/Wall.jsp");  //not perfect, user should get a message registration was successful!
        } catch (UserValidationException e) {
            errorDispatch(e.getMessage(), req, resp);
        }
    }

    private void errorDispatch(String err, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ERROR_ATTRIBUTE, err);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
    
    public static Date convertUtilDateToSqlDate(java.util.Date date){
        if(date != null) {
            Date sqlDate = new Date(date.getTime());
            return sqlDate;
        }
        return null;
    }
}
