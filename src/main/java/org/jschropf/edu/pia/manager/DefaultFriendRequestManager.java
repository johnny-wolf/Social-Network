package org.jschropf.edu.pia.manager;

import javax.transaction.Transactional;

import org.jschropf.edu.pia.dao.FriendRequestDao;
import org.jschropf.edu.pia.domain.FriendRequest;
import org.jschropf.edu.pia.domain.FriendRequestValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Manager for Friend requests
 * 
 * @author Jan Schropfer
 *
 */
@Service
@Transactional
public class DefaultFriendRequestManager implements FriendRequestManager{
	
	private FriendRequestDao friendRequestDao;
	
	@Autowired
	public DefaultFriendRequestManager(FriendRequestDao friendRequestDao){
		this.friendRequestDao = friendRequestDao;
	}

	@Override
	public void releaseFriendRequest(Long personId, Long targetId) throws FriendRequestValidationException {
		System.out.println("Trying to create new friend request");
		FriendRequest newRequest = friendRequestDao.createFriendRequest(personId, targetId);
		System.out.println(newRequest.toString());
		System.out.println("Testing Friend Request instance if already exists");
		
		if(!newRequest.isNew()) {
	        System.out.println("FriendRequest already exists, use save method for updates!");
	        return;
		} 

		System.out.println("Saving friend request");
        try {
        	friendRequestDao.save(newRequest);
        } catch (Exception e) {
        	System.out.println("Error while saving friend request!");
        	e.printStackTrace();
        }
        
        System.out.println("Friend Request saved");
	}
	
	@Override
	public void acceptFriendRequest(Long sourceId, Long targetId){
		System.out.println("Starting Friend Request Transaction");

		try {
        	friendRequestDao.acceptFriendRequest(sourceId, targetId);
        } catch (Exception e) {
        	System.out.println(e);
        }
		
        System.out.println("Friend Request accepted");
	}
	
	@Override
	public void declineFriendRequest(Long sourceId, Long targetId){
		System.out.println("Starting Friend Request Transaction");

		try {
        	friendRequestDao.declineFriendRequest(sourceId, targetId);
        } catch (Exception e) {
        	System.out.println(e);
        }

		System.out.println("Friend Request Declined");
	}
	
	@Override
	public boolean areFriends(Long sourceId, Long targetId){
		return friendRequestDao.areFriends(sourceId, targetId);
	}
	
	@Override
	public boolean areUnanswered(Long personId1, Long personId2){
		return friendRequestDao.areUnanswered(personId1, personId2);
	}
}
