package org.jschropf.edu.pia.manager;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.jschropf.edu.pia.dao.NotificationDao;
import org.jschropf.edu.pia.dao.PostDao;
import org.jschropf.edu.pia.dao.UserDao;
import org.jschropf.edu.pia.domain.Notification;
import org.jschropf.edu.pia.domain.Post;
import org.jschropf.edu.pia.domain.PostValidationException;
import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.domain.UserValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Manager for posts
 * 
 * @author Jan Schropfer
 *
 */
@Service
@Transactional
public class DefaultPostManager implements PostManager{
		
	private PostDao postDao;
	private UserDao userDao;
	private NotificationDao notificationDao;

	@Autowired
	public DefaultPostManager(PostDao postDao, NotificationDao notificationDao, UserDao userDao) {
        this.notificationDao = notificationDao;	
		this.postDao = postDao;
		this.userDao = userDao;
    }
	
	@Override
	public void releasePost(Long posterId, String title, String text, Long ownerId) throws PostValidationException{
		//Post newPost = postDao.createPost(title, text, posterId, ownerId);
		System.out.println("Constructing post - setting parameters");
    	Post newPost = new Post();
        newPost.setTitle(title);
        newPost.setText(text);
        System.out.println("Setting poster");
        newPost.setPoster(userDao.findById(posterId));
        System.out.println("Setting poster done: \n"+newPost.getPoster().toString());
        newPost.setOwnerId(ownerId);
        newPost.setDate(new Date());
        newPost.setPopularity(0);
        System.out.println("Constructing post - done");
		
		
		User poster = userDao.findById(posterId);
        
        System.out.println("Constructing notification"); 
        Notification notification = new Notification(text, ownerId, new Date(), "");
        notification.setDate(new Date());
        notification.setPersonId(ownerId);
        
        notification.setText(poster.getfName() + " " + poster.getlName() + " created a new post on your wall.");
        notification.setUrl("wall");
            notificationDao.createNotification(notification);
        if(notificationDao.createNotification(notification))
        	System.out.println("Constructing notification done");
		System.out.println(newPost.toString());
		System.out.println("Testing post instance if already exists");
		
		if(!newPost.isNew()) {
	        throw new RuntimeException("Post already exists, use save method for updates!");
		} 

	    System.out.println("Releasing post");
        try {
            postDao.save(newPost);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	@Override
	public List<Post> topTenFor(Long personId){
		return postDao.topTenFor(personId);
	}
	
	@Override
	public List<Post> wallFor(Long personId){
		return postDao.wallFor(personId);
	}
}
