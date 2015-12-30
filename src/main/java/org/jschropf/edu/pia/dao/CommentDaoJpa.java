package org.jschropf.edu.pia.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jschropf.edu.pia.domain.Comment;

public class CommentDaoJpa extends GenericDaoJpa<Comment> implements CommentDao{
	public CommentDaoJpa(EntityManager em) {
        super(em, Comment.class);
    }

	private UserDao userDao;
	
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
}
