package org.jschropf.edu.pia.dao;

import org.jschropf.edu.pia.domain.FriendRequest;

public interface FriendRequestDao extends GenericDao<FriendRequest>{

	public FriendRequest createFriendRequest(Long sourceId, Long targetId);

    public boolean acceptFriendRequest(Long sourceId, Long targetId);

    public boolean declineFriendRequest(Long sourceId, Long targetId);

    public boolean areFriends(Long personId1, Long personId2);

    public boolean areUnanswered(Long personId1, Long personId2); 
}
