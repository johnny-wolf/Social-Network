package org.jschropf.edu.pia.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jschropf.edu.pia.domain.Picture;
import org.jschropf.edu.pia.domain.Post;
import org.jschropf.edu.pia.domain.User;

public class PostDaoJpa extends GenericDaoJpa<Post> implements PostDao{
	private UserDao userDao;
	private NotificationDao notificationDao;
	
	public PostDaoJpa(EntityManager em) {
        super(em, Post.class);
    }

    @Override
    public Post findByPostId(long id) {
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
    
    public boolean createPost(String title, String text, long posterId, long ownerId, String picture) {        
        Post post = new Post();
        post.setTitle(title);
        post.setText(text);
        //post.setPosterId(posterId);
        post.setPoster(userDao.findById(posterId));
        post.setOwnerId(ownerId);
        post.setDate(new Date());
        post.setPopularity(0);
        //em.persist(post);
        
        //post = findByAttributes(post.getTitle(), post.getText(), post.getOwnerId(), post.getDate());
        
        if(picture != null && !picture.equals("")) {
            // pictureDao.createPicture(picture, post.getId());
	    Set<Picture> pictures = new HashSet<Picture>();
	    Picture pictureEntity = new Picture();
	    pictureEntity.setImage(picture);
	    pictureEntity.setPost(post);
	    pictures.add(pictureEntity);
	    post.setPictures(pictures);
        }
        em.persist(post);

        User poster = userDao.findById(posterId);
        notificationDao.createNotification(poster.getfName() + " " + poster.getlName() + " created a new post on your wall.", "wall", ownerId);
        return true;
    } 
    
    public List<Post> wallFor(Integer personId){
        Query q = em.createQuery("SELECT p FROM Post p WHERE p.ownerId=:personId ORDER BY p.date DESC");
        q.setParameter("personId", personId);
        return q.getResultList();
    } 

}
