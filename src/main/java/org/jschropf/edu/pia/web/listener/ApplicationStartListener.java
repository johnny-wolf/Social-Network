package org.jschropf.edu.pia.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener; 

import org.jschropf.edu.pia.domain.FriendRequest;
import org.jschropf.edu.pia.manager.CommentManager;
import org.jschropf.edu.pia.manager.FriendRequestManager;
import org.jschropf.edu.pia.manager.PostManager;
import org.jschropf.edu.pia.manager.UserManager;
import org.jschropf.edu.pia.web.auth.AuthenticationService;
import org.jschropf.edu.pia.web.filter.AuthenticationGuard;
import org.jschropf.edu.pia.web.servlet.AcceptFriendRequest;
import org.jschropf.edu.pia.web.servlet.AllUsers;
import org.jschropf.edu.pia.web.servlet.Comments;
import org.jschropf.edu.pia.web.servlet.CreateComment;
import org.jschropf.edu.pia.web.servlet.CreateFriendRequest;
import org.jschropf.edu.pia.web.servlet.CreatePost;
import org.jschropf.edu.pia.web.servlet.DeclineFriendRequest;
import org.jschropf.edu.pia.web.servlet.FriendRequests;
import org.jschropf.edu.pia.web.servlet.Friends;
import org.jschropf.edu.pia.web.servlet.Login;
import org.jschropf.edu.pia.web.servlet.Logout;
import org.jschropf.edu.pia.web.servlet.ProfileEdit;
import org.jschropf.edu.pia.web.servlet.Register;
import org.jschropf.edu.pia.web.servlet.UserProfile;
import org.jschropf.edu.pia.web.servlet.Wall;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
    //private ApplicationContext ctx;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //ctx = new ApplicationContext();

    	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
    	AuthenticationService auth = ctx.getBean(AuthenticationService.class); 
    	
    	/*sce.getServletContext().addServlet("login", new Login()).addMapping("/login");
        sce.getServletContext().addServlet("logout", new Logout()).addMapping("/logout");
        sce.getServletContext().addServlet("register", new Register(ctx.getBean(UserManager.class))).addMapping("/register");
        //sce.getServletContext().addServlet("wall", new Wall(ctx.getBean(PostManager.class), ctx.getBean(UserManager.class))).addMapping("/wall");
        sce.getServletContext().addServlet("createPost", new CreatePost(ctx.getBean(PostManager.class), ctx.getBean(FriendRequestManager.class))).addMapping("/createPost");
        sce.getServletContext().addServlet("createComment", new CreateComment(ctx.getBean(CommentManager.class))).addMapping("/createComment");
        sce.getServletContext().addServlet("comments", new Comments(ctx.getBean(CommentManager.class))).addMapping("/comments");
        sce.getServletContext().addServlet("friendRequest", new CreateFriendRequest(ctx.getBean(FriendRequestManager.class))).addMapping("/friendRequest");
        sce.getServletContext().addServlet("friendRequests", new FriendRequests(ctx.getBean(UserManager.class))).addMapping("/friendRequests");
        sce.getServletContext().addServlet("acceptFriendRequest", new AcceptFriendRequest(ctx.getBean(FriendRequestManager.class))).addMapping("/acceptFriendRequest");
        sce.getServletContext().addServlet("friends", new Friends(ctx.getBean(UserManager.class))).addMapping("/friends");
        sce.getServletContext().addServlet("declineFriendRequest", new DeclineFriendRequest(ctx.getBean(FriendRequestManager.class))).addMapping("/declineFriendRequest");
        sce.getServletContext().addServlet("allUsers", new AllUsers(ctx.getBean(UserManager.class))).addMapping("/allUsers");
        sce.getServletContext().addServlet("userProfile", new UserProfile(ctx.getBean(UserManager.class))).addMapping("/userProfile");
        sce.getServletContext().addServlet("editProfile", new ProfileEdit(ctx.getBean(UserManager.class))).addMapping("/editProfile");*/

        
        sce.getServletContext().addFilter("authFilter", new AuthenticationGuard(auth)).addMappingForUrlPatterns(null, false, "/secret/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //ctx.destroy();
    }
}
