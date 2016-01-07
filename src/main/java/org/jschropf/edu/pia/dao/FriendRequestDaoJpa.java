package org.jschropf.edu.pia.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.jschropf.edu.pia.domain.Comment;
import org.jschropf.edu.pia.domain.FriendRequest;
import org.springframework.stereotype.Repository;

@Repository
public class FriendRequestDaoJpa extends GenericDaoJpa<FriendRequest> implements FriendRequestDao {
	public FriendRequestDaoJpa(EntityManager em) {
        super(em, FriendRequest.class);
    }
	
    public FriendRequest createFriendRequest(long sourceId, long targetId){
        //if already requested
        Query q = em.createQuery("SELECT COUNT(f) FROM FriendRequest f WHERE f.sourceId=:sourceId AND f.targetId=:targetId AND f.status <> 'DECLINED'");
        q.setParameter("sourceId", sourceId);
        q.setParameter("targetId", targetId);
        if ((Long) q.getSingleResult() > 0) {
            return null;
        }
        //this is not a mistake - cross requesting
        Query q2 = em.createQuery("SELECT COUNT(f) FROM FriendRequest f WHERE f.sourceId=:sourceId AND f.targetId=:targetId AND f.status <> 'DECLINED'");
        q2.setParameter("sourceId", targetId);
        q2.setParameter("targetId", sourceId);
        if ((Long) q2.getSingleResult() > 0) {
            Query q3 = em.createQuery("UPDATE FriendRequest f SET f.status='ACCEPTED' WHERE p.sourceId=:sourceId AND p.targetId=:targetId");
            q3.setParameter("sourceId", targetId);
            q3.setParameter("targetId", sourceId);
            q3.executeUpdate();
            return null;
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setDate(new Date());
        friendRequest.setSourceId(sourceId);
        // friendRequest.setSource(personDao.findById(sourceId));
        friendRequest.setTargetId(targetId);
        friendRequest.setStatus("UNANSWERED");
        em.persist(friendRequest);
        return friendRequest;
    }
    
    public boolean acceptFriendRequest(long sourceId, long targetId) {
        Query q = em.createQuery("UPDATE FriendRequest f SET f.status='ACCEPTED' WHERE f.sourceId=:sourceId AND f.targetId=:targetId");
        q.setParameter("sourceId", sourceId);
        q.setParameter("targetId", targetId);
        q.executeUpdate();
        return true;
    } 
    
    public boolean declineFriendRequest(long sourceId, long targetId) {
        Query q = em.createQuery("UPDATE FriendRequest f SET f.status='DECLINED' WHERE (f.sourceId=:sourceId AND f.targetId=:targetId) OR (f.sourceId=:targetId AND f.targetId=:sourceId)");
        q.setParameter("sourceId", sourceId);
        q.setParameter("targetId", targetId);
        q.executeUpdate();
        return true;
    }
    
    public boolean areFriends(long personId1, long personId2){
	    if(personId1 == 0 || personId2 == 0) return false;
	    if(personId1 == personId2) return false;
	    Query q = em.createQuery("SELECT COUNT(r) FROM FriendRequest r WHERE r.status='ACCEPTED' AND ((r.sourceId=:personId1 AND r.targetId=:personId2) OR (r.sourceId=:personId2 AND r.targetId=:personId1))");
	    q.setParameter("personId1", personId1);
	    q.setParameter("personId2", personId2);
	    if ((Long) q.getSingleResult() > 0) {
		    return true;
		    
	    } else {
		    return false;
	    }
    } 
    
    public boolean areUnanswered(long personId1, long personId2){
	    if(personId1 == 0 || personId2 == 0) return false;
	    if(personId1 == personId2) return false;
	    Query q = em.createQuery("SELECT COUNT(r) FROM FriendRequest r WHERE r.status='UNANSWERED' AND ((r.sourceId=:personId1 AND r.targetId=:personId2) OR (r.sourceId=:personId2 AND r.targetId=:personId1))");
	    q.setParameter("personId1", personId1);
	    q.setParameter("personId2", personId2);
	    if ((Long) q.getSingleResult() > 0) {
		    return true;
		    
	    } else {
		    return false;
	    }
    }
}
