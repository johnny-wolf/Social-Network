package org.jschropf.edu.pia.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.jschropf.edu.pia.domain.User;
import org.springframework.stereotype.Repository;

/**
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
@Repository
public class UserDaoJpa extends GenericDaoJpa<User> implements UserDao, Serializable {
    private static final long serialVersionUID = 1L;
    public UserDaoJpa(EntityManager em) {
        super(em, User.class);
    }

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.username = :uname", User.class);
        q.setParameter("uname", username);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            //no result found, ensuring the behaviour described by interface specification
            //see javadoc of the findByUsername method.
            return null;
        }
    }
    
    @Override
    public User findById(long id) {
        TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.id = :uid", User.class);
        q.setParameter("uid", id);
       
        try {
        	User temp = q.getSingleResult();
            return temp;
        } catch (NoResultException e) {
        	System.out.println("No user with id: "+id+" was found");
            //no result found, ensuring the behaviour described by interface specification
            //see javadoc of the findByUsername method.
            return null;
        }
    }
}
