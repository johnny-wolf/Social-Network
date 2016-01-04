package org.jschropf.edu.pia;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.jschropf.edu.pia.dao.CommentDao;
import org.jschropf.edu.pia.dao.CommentDaoJpa;
import org.jschropf.edu.pia.dao.FriendRequestDao;
import org.jschropf.edu.pia.dao.FriendRequestDaoJpa;
import org.jschropf.edu.pia.dao.NotificationDao;
import org.jschropf.edu.pia.dao.NotificationDaoJpa;
import org.jschropf.edu.pia.dao.PictureDao;
import org.jschropf.edu.pia.dao.PictureDaoJpa;
import org.jschropf.edu.pia.dao.PostDao;
import org.jschropf.edu.pia.dao.PostDaoJpa;
import org.jschropf.edu.pia.dao.UserDao;
import org.jschropf.edu.pia.dao.UserDaoJpa;
import org.jschropf.edu.pia.manager.DefaultPostManager;
import org.jschropf.edu.pia.manager.DefaultUserManager;
import org.jschropf.edu.pia.manager.PostManager;
import org.jschropf.edu.pia.manager.UserManager;
import org.jschropf.edu.pia.utils.Encoder;
import org.jschropf.edu.pia.utils.PasswordHashEncoder;
import org.jschropf.edu.pia.web.auth.AuthenticationService;
//import org.jschropf.edu.pia.web.servlet.CreatePost;

/**
 * Application context holds references to all parts of the application,
 * manages their creation and provides them wherever needed.
 *
 * TODO currently are all instances held by the context hard-coded.
 * TODO the whole mechanism could be made dynamic using String ids to identify each held instance
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public class ApplicationContext {

    //persistence
    private EntityManager em;
    private UserDao userDao;
    private PostDao postDao;
    private NotificationDao notificationDao;
    private PictureDao pictureDao;
    private FriendRequestDao friendRequestDao;
    private CommentDao commentDao;

    //business
    private UserManager userManager;
    private PostManager postManager;
    private Encoder encoder;

    //web
    private AuthenticationService authenticationService;
    //private CreatePost createPost;

    public ApplicationContext() {
        //TODO persistence unit name should be taken from a property file, not hard-coded!
        em = Persistence.createEntityManagerFactory("org.danekja.edu.pia").createEntityManager();
        userDao = new UserDaoJpa(em);
        postDao = new PostDaoJpa(em);
        notificationDao = new NotificationDaoJpa(em);
        pictureDao = new PictureDaoJpa(em);
        friendRequestDao = new FriendRequestDaoJpa(em);
        commentDao = new CommentDaoJpa(em);
        encoder = new PasswordHashEncoder();
        userManager = new DefaultUserManager(userDao, encoder);
        postManager = new DefaultPostManager(postDao);
        authenticationService = new AuthenticationService(userManager);
        //createPost = new CreatePost(userManager, postManager);
    }

	public void destroy() {
        em.close();
    }

    public EntityManager getEm() {
        return em;
    }

    public UserDao getUserDao() {
        return userDao;
    }
    
    public PostDao getPostDao() {
        return postDao;
    }
    
    public NotificationDao getNotificationDao() {
        return notificationDao;
    }
    
    public PictureDao getPictureDao() {
		return pictureDao;
	}

	public FriendRequestDao getFriendRequestDao() {
		return friendRequestDao;
	}

	public CommentDao getCommentDao() {
		return commentDao;
	}

    public UserManager getUserManager() {
        return userManager;
    }

    public Encoder getEncoder() {
        return encoder;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }
    
    /*public CreatePost getCreatePost(){
    	return createPost;
    }*/

	public PostManager getPostManager() {
		return postManager;
	}
}
