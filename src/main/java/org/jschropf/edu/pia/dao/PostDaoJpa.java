package org.jschropf.edu.pia.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jschropf.edu.pia.domain.Picture;
import org.jschropf.edu.pia.domain.Post;
import org.jschropf.edu.pia.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostDaoJpa extends GenericDaoJpa<Post> implements PostDao{
	@Autowired
	private UserDao userDao;
	@Autowired
	private NotificationDao notificationDao;
	@Autowired
	private PictureDao pictureDao; 
	
	public PostDaoJpa() {
        super(Post.class);
        this.userDao = userDao;
        this.notificationDao = notificationDao;
    }

    @Override
    public Post findByPostId(long id) {
    	System.out.println("Searching for post with id: "+id);
        TypedQuery<Post> q = em.createQuery("SELECT p FROM Post p WHERE p.id = :pid", Post.class);
        q.setParameter("pid", id);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            //no result found, ensuring the behaviour described by interface specification
            //see javadoc of the findByUsername method.
            
        	return null;
        }
    }
    
    /*public Post createPost(String title, String text, long posterId, long ownerId) {        
        System.out.println("Constructing post - setting parameters");
    	Post post = new Post();
        post.setTitle(title);
        post.setText(text);
        System.out.println("Setting poster");
        post.setPoster(userDao.findById(posterId));
        System.out.println("Setting poster done: \n"+post.getPoster().toString());
        post.setOwnerId(ownerId);
        post.setDate(new Date());
        post.setPopularity(0);
        System.out.println("Constructing post - done");
        
        return post;
    }*/
    
    @Override
    public List<Post> topTenFor(Long personId){
        Query q = em.createQuery("SELECT p FROM Post p WHERE p.ownerId=:personId ORDER BY p.popularity DESC");
        q.setParameter("personId", personId);
        List<Post> allResults = q.getResultList();
        List<Post> topTen = new ArrayList<Post>();
        int restrict = (allResults.size() < 10) ? allResults.size() : 10;
        
        for(int i = 0; i < restrict; i++) {
            topTen.add(allResults.get(i));
        }
        
        return topTen;
    } 
    
    @Override
    public void updatePostId(Long posterId, Long postId){
    	Query q = em.createQuery("UPDATE Post SET posterId=:personId WHERE id=:postId");
    	
    	try{
    	em.getTransaction().begin();
    	q.setParameter("personId", posterId);
    	q.setParameter("postId", postId);
    	q.executeUpdate();
		}catch(Exception e){
			em.getTransaction().rollback();
		}
		
    	em.getTransaction().commit();
	}
    
    @Override
    public Post findByDate(Date date){
    	java.sql.Date sDate = new java.sql.Date(date.getTime());
    	Query q = em.createQuery("SELECT p FROM Post p WHERE p.date=:postDate", Post.class);
    	q.setParameter("postDate", sDate);
    	
    	return (Post)q.getSingleResult();
    }
    
    @Override
    public Post findByTexts(String text, String title){
    	Query q = em.createQuery("SELECT p FROM Post p WHERE p.text=:postText AND p.title=:postTitle");
    	q.setParameter("postText", text);
    	q.setParameter("postTitle", title);
    	
    	return (Post)q.getSingleResult();
    }
    
    public List<Post> wallFor(Long personId){
        Query q = em.createQuery("SELECT p FROM Post p WHERE p.ownerId=:personId ORDER BY p.date DESC");
        q.setParameter("personId", personId);
        
        return q.getResultList();
    } 

}
