package org.jschropf.edu.pia.manager;

import java.util.List;

import javax.transaction.Transactional;

import org.jschropf.edu.pia.dao.PostDao;
import org.jschropf.edu.pia.dao.UserDao;
import org.jschropf.edu.pia.domain.Post;
import org.jschropf.edu.pia.domain.PostValidationException;
import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.domain.UserValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DefaultPostManager implements PostManager{
	
	@Autowired
	private PostDao postDao;

	public DefaultPostManager() {
        this.postDao = postDao;
    }
	
	@Override
	public void releasePost(Long posterId, String title, String text, Long ownerId) throws PostValidationException{
		Post newPost = postDao.createPost(title, text, posterId, ownerId);
		System.out.println(newPost.toString());
		System.out.println("Testing post instance if already exists");
		if(!newPost.isNew()) {
	        throw new RuntimeException("Post already exists, use save method for updates!");
		} 
	        //newPost.validate();
		System.out.println("Testing post if already exists");    
	   /* Post existinCheck = postDao.findByPostId(newPost.getId());
	    if(existinCheck != null) {
	    	throw new PostValidationException("Post already exists!");
	    }*/
	    
	    System.out.println("Starting Post Transaction");
	    //postDao.startTransaction();   
        try {
            postDao.save(newPost);
            //newPost.setPoster(userDao.findById(posterId));
        } catch (Exception e) {
            //postDao.rollbackTransaction();
        }
        //postDao.commitTransaction();
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
