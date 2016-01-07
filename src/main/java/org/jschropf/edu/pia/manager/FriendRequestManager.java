package org.jschropf.edu.pia.manager;

import org.jschropf.edu.pia.domain.FriendRequest;
import org.jschropf.edu.pia.domain.FriendRequestValidationException;

public interface FriendRequestManager {
	void releaseFriendRequest(FriendRequest newRequest) throws FriendRequestValidationException;

	void acceptFriendRequest(Long sourceId, Long targetId); 
}
