package org.jschropf.edu.pia.dao;

import java.util.List;

import org.jschropf.edu.pia.domain.Notification;

/**
 * 
 * @author Jan Schropfer
 *
 */
public interface NotificationDao extends GenericDao<Notification>{

	/**
	 * 
	 * @param id the id of desired notification
	 * @return notification or null if not found
	 */
	public Notification findByNotificationId(long id);
	
	/**
	 * creates notification
	 * 
	 * @param text the text of notification
	 * @param url the url where it happens
	 * @param personId the id of person for whom is the notification
	 * @return true if created, otherwise false
	 */
	public boolean createNotification(String text, String url, long personId);
	
	/**
	 * 
	 * @param personId the id of person with notifications
	 * @return list of notifications
	 */
    public List<Notification> notificationsFor(long personId); 
	
}
