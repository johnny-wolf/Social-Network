package org.jschropf.edu.pia.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jschropf.edu.pia.domain.Notification;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationDaoJpa extends GenericDaoJpa<Notification> implements NotificationDao{
	public NotificationDaoJpa() {
        super(Notification.class);
    }
	
	@Override
    public Notification findByNotificationId(long id) {
        TypedQuery<Notification> q = em.createQuery("SELECT p FROM Comment p WHERE p.id = :pid", Notification.class);
        q.setParameter("pid", id);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
 
            return null;
        }
    } 
	
	/*public boolean createNotification(String text, String url, long personId) {
		System.out.println("Creating Notification - Setting parameters");
        Notification notification = new Notification(text, personId, new Date(), url);
        notification.setText(text);
        notification.setDate(new Date());
        notification.setUrl(url);
        notification.setPersonId(personId);
        System.out.println("Creating Notification - Setting parameters - Done");
        em.persist(notification);
        
        return true;
    }*/

	public boolean createNotification(Notification notification) {
        System.out.println("Creating Notification - Setting parameters - Done");
		em.persist(notification);
		return true;
	}
	
    public List<Notification> notificationsFor(long personId) {
        Query q = em.createQuery("SELECT n FROM Notification n WHERE n.personId=:personId ORDER BY n.date DESC");
        q.setParameter("personId", personId);
        
        return q.getResultList();
    } 
}
