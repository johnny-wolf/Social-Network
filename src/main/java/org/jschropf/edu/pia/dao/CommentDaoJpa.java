package org.jschropf.edu.pia.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jschropf.edu.pia.domain.Comment;
import org.jschropf.edu.pia.domain.Post;
import org.jschropf.edu.pia.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDaoJpa extends GenericDaoJpa<Comment> implements CommentDao{

    @Autowired
    private NotificationDao notificationDao;
    
    @Autowired
    private PostDao postDao;
    
    @Autowired
    private UserDao userDao;
	
	public CommentDaoJpa() {
        super(Comment.class);
        this.notificationDao = notificationDao;
        this.postDao = postDao;
        this.userDao = userDao;
    }

    
    @Override
    public Comment createComment(String text, Long personId, Long postId) {
        Comment comment = new Comment();
        comment.setDate(new Date());
        comment.setCommenter(userDao.findById(personId));
        comment.setPostId(postId);
        comment.setText(text);
        
        Post post = postDao.findByPostId(postId);

        try{
	        Query q = em.createNativeQuery("UPDATE jschropf_SM_posts SET popularity = (popularity + 1) WHERE id=" + post.getId() + ";");
	        q.executeUpdate();
	        em.flush();
	    } catch (Exception e) {
	    	System.out.println("failed to execute update");
	    }
        
        Long ownerId = post.getOwnerId();
        Long posterId = post.getPoster().getId();
        User commenter = userDao.findById(personId);
        
        if(ownerId != personId)
            notificationDao.createNotification(commenter.getfName() + " " + commenter.getlName() + " commented on the post on your wall.", "wall", ownerId);
        if(posterId != personId)
            notificationDao.createNotification(commenter.getfName() + " " + commenter.getlName() + " commented on the post you have written.", "wall?ownerId=" + ownerId.toString(), posterId);
        
        return comment;
    }
	
    @Override
    public Comment findByCommentId(long id) {
        TypedQuery<Comment> q = em.createQuery("SELECT p FROM Comment p WHERE p.id = :pid", Comment.class);
        q.setParameter("pid", id);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            //no result found, ensuring the behaviour described by interface specification
            //see javadoc of the findByUsername method.
            
        	return null;
        }
    } 
    
    @Override
    public List<Comment> commentsFor(Long postId) {
        Query q = em.createQuery("SELECT c FROM Comment c JOIN FETCH c.commenter WHERE c.postId=:postId ORDER BY c.id DESC");
        q.setParameter("postId", postId);
        
        return q.getResultList();
    } 
}
