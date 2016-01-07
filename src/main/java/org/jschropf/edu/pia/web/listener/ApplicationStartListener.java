package org.jschropf.edu.pia.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.jschropf.edu.pia.ApplicationContext;
import org.jschropf.edu.pia.domain.FriendRequest;
import org.jschropf.edu.pia.web.filter.AuthenticationGuard;
import org.jschropf.edu.pia.web.servlet.Comments;
import org.jschropf.edu.pia.web.servlet.CreateComment;
import org.jschropf.edu.pia.web.servlet.CreateFriendRequest;
import org.jschropf.edu.pia.web.servlet.CreatePost;
import org.jschropf.edu.pia.web.servlet.Login;
import org.jschropf.edu.pia.web.servlet.Register;
import org.jschropf.edu.pia.web.servlet.SecretServlet;
import org.jschropf.edu.pia.web.servlet.Wall;

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

        sce.getServletContext().addServlet("login", new Login(ctx.getAuthenticationService(),ctx.getEm())).addMapping("/login");
        sce.getServletContext().addServlet("register", new Register(ctx.getUserManager(),ctx.getEm())).addMapping("/register");
        sce.getServletContext().addServlet("secret", new SecretServlet()).addMapping("/secret/vip");
        sce.getServletContext().addServlet("createPost", new CreatePost(ctx.getPostManager(), ctx.getFriendRequestDao(), ctx.getPostDao())).addMapping("/createPost");
        sce.getServletContext().addServlet("wall", new Wall(ctx.getPostDao())).addMapping("/wall");
        sce.getServletContext().addServlet("createComment", new CreateComment(ctx.getCommentDao(), ctx.getCommentManager())).addMapping("/createComment");
        sce.getServletContext().addServlet("comments", new Comments(ctx.getCommentDao())).addMapping("/comments");
        sce.getServletContext().addServlet("friendRequest", new CreateFriendRequest(ctx.getFriendRequestDao(), ctx.getFriendRequestManager())).addMapping("/friendRequest");
        
        sce.getServletContext().addFilter("authFilter", new AuthenticationGuard(ctx.getAuthenticationService())).addMappingForUrlPatterns(null, false, "/secret/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ctx.destroy();
    }
}
