package org.jschropf.edu.pia.manager;

import org.jschropf.edu.pia.domain.FriendRequest;
import org.jschropf.edu.pia.domain.FriendRequestValidationException;

/**
 * Manager for Friend requests
 * 
 * @author Jan Schropfer
 *
 */
public interface FriendRequestManager {
	/**
	 * method for accepting friend request
	 * 
	 * @param sourceId id of requester
	 * @param targetId id of target
	 */
	void acceptFriendRequest(Long sourceId, Long targetId);

	/**
	 * method for declining friend request
	 * 
	 * @param sourceId requester id
	 * @param targetId target id
	 */
	void declineFriendRequest(Long sourceId, Long targetId);

	/**
	 * Method for deciding if two users are friends
	 * 
	 * @param sourceId first person
	 * @param targetId second person
	 * @return true if they are friends, otherwise null
	 */
	boolean areFriends(Long sourceId, Long targetId);

	/**
	 * Method for creating friend request
	 * 
	 * @param personId requester id
	 * @param targetId target id
	 * @throws FriendRequestValidationException
	 */
	void releaseFriendRequest(Long personId, Long targetId) throws FriendRequestValidationException;

	/**
	 * Method for finding if two users already have friend request
	 * @param personId1 first user
	 * @param personId2 second user
	 * @return true if friend request exists, false otherwise
	 */
	public boolean areUnanswered(Long personId1, Long personId2); 
}
