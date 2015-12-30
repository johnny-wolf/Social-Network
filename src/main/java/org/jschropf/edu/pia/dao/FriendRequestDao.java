package org.jschropf.edu.pia.dao;

import org.jschropf.edu.pia.domain.FriendRequest;

public interface FriendRequestDao extends GenericDao<FriendRequest>{

	public boolean createFriendRequest(long sourceId, long targetId);

    public boolean acceptFriendRequest(long sourceId, long targetId);

    public boolean declineFriendRequest(long sourceId, long targetId);

    public boolean areFriends(long personId1, long personId2);

    public boolean areUnanswered(long personId1, long personId2); 
}
