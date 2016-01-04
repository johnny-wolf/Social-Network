package org.jschropf.edu.pia.web.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Forwarding extends javax.servlet.http.HttpServlet{
	private String message;

	  public void init() throws ServletException
	  {

	  }

	  public void doGet(HttpServletRequest request,
	                    HttpServletResponse response)
	            throws ServletException, IOException
	  {
	      String pathInfo = request.getPathInfo();
	      request.getRequestDispatcher("/WEB-INF/pages/" + pathInfo).forward(request, response);

	  }

	  public void destroy()
	  {
	      // do nothing.
	  }
}
