package org.jschropf.edu.pia.dao;

import org.jschropf.edu.pia.domain.FriendRequest;
/**
 * 
 * @author Jan Schropfer
 *
 */
public interface FriendRequestDao extends GenericDao<FriendRequest>{
	
	/**
	 * creates friend request
	 * 
	 * @param sourceId the id of user who wants friendship
	 * @param targetId the id of target
	 * @return new friend request
	 */
	public boolean createFriendRequest(Long sourceId, Long targetId);
	
	/**
	 * accepting friend request
	 * 
	 * @param sourceId the id of user who wants friendship
	 * @param targetId the id of target
	 * @column target column
	 * @status target status
	 * @return true if done correctly otherwise false
	 */
	 public boolean acceptFriendRequest(Long sourceId, Long targetId, String column, String status);

	/**
	 * decline friend request
	 * 
	 * @param sourceId the id of user who wants friendship
	 * @param targetId the id of target
	 * @return true if done correctly otherwise false
	 */
	 public boolean declineFriendRequest(Long sourceId, Long targetId, String column, String status);

	/**
	 * are friends
	 * 
	 * @param sourceId the id of first user
	 * @param targetId the id of second user
	 * @return true if yes otherwise false
	 */
    public boolean areFriends(Long personId1, Long personId2);

    /**
     * are there friend requests pending
     * 
     * @param personId1 the id of first user
     * @param personId2 the id of second user
     * @return true if unanswered request exist, otherwise false
     */
    public boolean areUnanswered(Long personId1, Long personId2); 
}
