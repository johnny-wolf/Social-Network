package org.jschropf.edu.pia.manager;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.jschropf.edu.pia.dao.CommentDao;
import org.jschropf.edu.pia.dao.NotificationDao;
import org.jschropf.edu.pia.dao.PostDao;
import org.jschropf.edu.pia.dao.UserDao;
import org.jschropf.edu.pia.domain.Comment;
import org.jschropf.edu.pia.domain.CommentValidationException;
import org.jschropf.edu.pia.domain.Notification;
import org.jschropf.edu.pia.domain.Post;
import org.jschropf.edu.pia.domain.PostValidationException;
import org.jschropf.edu.pia.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DefaultCommentManager implements CommentManager{
	
	private CommentDao commentDao;
	private PostDao postDao;
	private UserDao userDao;
	private NotificationDao notificationDao;
	
	@Autowired
	public DefaultCommentManager(CommentDao commentDao, UserDao userDao, PostDao postDao, NotificationDao notificationDao) {
        this.commentDao = commentDao;
        this.userDao = userDao;
        this.postDao = postDao;
        this.notificationDao = notificationDao;
    }
	
	@Override
	public void releaseComment(Long posterId, String text, Long postId) throws CommentValidationException{
		commentDao.createComment(text, posterId, postId);
		Comment comment = new Comment();
        comment.setDate(new Date());
        comment.setCommenter(userDao.findById(posterId));
        comment.setPostId(postId);
        comment.setText(text);
        
        Post post = postDao.findByPostId(postId);
        
        Long ownerId = post.getOwnerId();
        User commenter = userDao.findById(posterId);
        Long posterId2 = post.getPoster().getId();
        
		System.out.println("Creating Notification - Setting parameters");
        Notification notification = new Notification(text, posterId, new Date(), "");
        notification.setDate(new Date());
        notification.setPersonId(ownerId);
        
        if(ownerId != posterId){
        	notification.setText(commenter.getfName() + " " + commenter.getlName() + " commented on the post on your wall.");
        	notification.setUrl("wall");
            notificationDao.createNotification(notification);
            }
        if(posterId != posterId2){
        	notification.setText(commenter.getfName() + " " + commenter.getlName() + " commented on the post you have written.");
        	notification.setUrl("wall?ownerId=" + ownerId.toString());
            notificationDao.createNotification(notification);
            }
        
		System.out.println(comment.toString());
		System.out.println("Testing comment instance if already exists");
		
		if(!comment.isNew()) {
	        throw new RuntimeException("Comment already exists, use save method for updates!");
		} 
		
		System.out.println("Saving comment");
        
		try {
        	commentDao.save(comment);
            //newPost.setPoster(userDao.findById(posterId));
        } catch (Exception e) {
        	System.out.println("Comment not saved properly!");
        	e.printStackTrace();
        }
        System.out.println("Comment saved");
	}
	
	@Override
	public List<Comment> commentsFor(Long postId){
		List<Comment> comments = commentDao.commentsFor(postId);
		return comments;
	}
}
