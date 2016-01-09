package org.jschropf.edu.pia.manager;

import org.jschropf.edu.pia.domain.FriendRequest;
import org.jschropf.edu.pia.domain.FriendRequestValidationException;

public interface FriendRequestManager {
	//void releaseFriendRequest(FriendRequest newRequest) throws FriendRequestValidationException;

	void acceptFriendRequest(Long sourceId, Long targetId);

	void declineFriendRequest(Long sourceId, Long targetId);

	boolean areFriends(Long sourceId, Long targetId);

	void releaseFriendRequest(Long personId, Long targetId) throws FriendRequestValidationException; 
}
