package org.jschropf.edu.pia.manager;

import org.jschropf.edu.pia.dao.CommentDao;
import org.jschropf.edu.pia.domain.Comment;
import org.jschropf.edu.pia.domain.CommentValidationException;
import org.jschropf.edu.pia.domain.PostValidationException;

public class DefaultCommentManager implements CommentManager{
	private CommentDao commentDao;

	public DefaultCommentManager(CommentDao commentDao) {
        this.commentDao = commentDao;
    }
	
	@Override
	public void releaseComment(Comment newComment, Long posterId) throws CommentValidationException{
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
	    commentDao.startTransaction();   
        try {
        	commentDao.save(newComment);
            //newPost.setPoster(userDao.findById(posterId));
        } catch (Exception e) {
        	commentDao.rollbackTransaction();
        }
        commentDao.commitTransaction();
        System.out.println("Comment Transaction Complete");
	}
}
