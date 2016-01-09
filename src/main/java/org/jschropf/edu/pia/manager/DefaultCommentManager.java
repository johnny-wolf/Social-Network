package org.jschropf.edu.pia.manager;

import java.util.List;

import javax.transaction.Transactional;

import org.jschropf.edu.pia.dao.CommentDao;
import org.jschropf.edu.pia.domain.Comment;
import org.jschropf.edu.pia.domain.CommentValidationException;
import org.jschropf.edu.pia.domain.PostValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DefaultCommentManager implements CommentManager{
	@Autowired
	private CommentDao commentDao;

	public DefaultCommentManager() {
        this.commentDao = commentDao;
    }
	
	@Override
	public void releaseComment(Long posterId, String text, Long postId) throws CommentValidationException{
		Comment newComment = commentDao.createComment(text, posterId, postId);
		System.out.println(newComment.toString());
		System.out.println("Testing comment instance if already exists");
		if(!newComment.isNew()) {
	        throw new RuntimeException("Comment already exists, use save method for updates!");
		} 
	        //newPost.validate();
		System.out.println("Testing comment if already exists");    
	   /* Post existinCheck = postDao.findByPostId(newPost.getId());
	    if(existinCheck != null) {
	    	throw new PostValidationException("Post already exists!");
	    }*/
	    
	    System.out.println("Starting Comment Transaction");
	    //commentDao.startTransaction();   
        try {
        	commentDao.save(newComment);
            //newPost.setPoster(userDao.findById(posterId));
        } catch (Exception e) {
        	//commentDao.rollbackTransaction();
        }
        //commentDao.commitTransaction();
        System.out.println("Comment Transaction Complete");
	}
	
	@Override
	public List<Comment> commentsFor(Long postId){
		List<Comment> comments = commentDao.commentsFor(postId);
		return comments;
	}
}
