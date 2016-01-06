package org.jschropf.edu.pia.dao;

import java.util.List;

import org.jschropf.edu.pia.domain.Notification;

public interface NotificationDao extends GenericDao<Notification>{

	public Notification findByNotificationId(long id);
	
	public boolean createNotification(String text, String url, long personId);

    public List<Notification> notificationsFor(long personId); 
	
}
