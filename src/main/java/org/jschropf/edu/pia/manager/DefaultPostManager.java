package org.jschropf.edu.pia.manager;

import org.jschropf.edu.pia.dao.PostDao;
import org.jschropf.edu.pia.dao.UserDao;
import org.jschropf.edu.pia.domain.Post;
import org.jschropf.edu.pia.domain.PostValidationException;
import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.domain.UserValidationException;

public class DefaultPostManager implements PostManager{
	
	private UserDao userDao;
	private PostDao postDao;

	public DefaultPostManager(PostDao postDao) {
        this.postDao = postDao;
    }
	
	@Override
	public void releasePost(Post newPost) throws PostValidationException{
		if(!newPost.isNew()) {
	        throw new RuntimeException("Post already exists, use save method for updates!");
		}    
	        //newPost.validate();
	        
	    Post existinCheck = postDao.findByPostId(newPost.getId());
	    if(existinCheck != null) {
	    	throw new PostValidationException("Post already exists!");
	    }
	    
	    postDao.startTransaction();
        try {
            postDao.save(newPost);
        } catch (Exception e) {
            postDao.rollbackTransaction();
        }
        postDao.commitTransaction();
	    
	}
}
