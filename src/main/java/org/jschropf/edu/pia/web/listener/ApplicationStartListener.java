package org.jschropf.edu.pia.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.jschropf.edu.pia.ApplicationContext;
import org.jschropf.edu.pia.web.filter.AuthenticationGuard;
import org.jschropf.edu.pia.web.servlet.Login;
import org.jschropf.edu.pia.web.servlet.Register;
import org.jschropf.edu.pia.web.servlet.SecretServlet;

/**
 * Application startup listener. Handles registration of servlets
 * and injection of their dependencies.
 *
 * Date: 20.11.15
 *
 * @author Jakub Danek
 */
@WebListener
public class ApplicationStartListener implements ServletContextListener {

    private ApplicationContext ctx;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ctx = new ApplicationContext();

        sce.getServletContext().addServlet("login", new Login(ctx.getAuthenticationService())).addMapping("/login");
        sce.getServletContext().addServlet("register", new Register(ctx.getUserManager())).addMapping("/register");
        sce.getServletContext().addServlet("secret", new SecretServlet()).addMapping("/secret/vip");

        sce.getServletContext().addFilter("authFilter", new AuthenticationGuard(ctx.getAuthenticationService())).addMappingForUrlPatterns(null, false, "/secret/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ctx.destroy();
    }
}
