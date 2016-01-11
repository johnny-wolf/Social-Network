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
	
	private CommentDao commentDao;
	
	@Autowired
	public DefaultCommentManager(CommentDao commentDao) {
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
		
		System.out.println("Saving comment");
        
		try {
        	commentDao.save(newComment);
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
