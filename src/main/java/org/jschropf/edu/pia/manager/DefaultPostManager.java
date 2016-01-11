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

	@Autowired
	public DefaultPostManager(PostDao postDao) {
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
