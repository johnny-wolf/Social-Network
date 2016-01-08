package org.jschropf.edu.pia.manager;

import org.jschropf.edu.pia.dao.FriendRequestDao;
import org.jschropf.edu.pia.domain.FriendRequest;
import org.jschropf.edu.pia.domain.FriendRequestValidationException;

public class DefaultFriendRequestManager implements FriendRequestManager{
	private FriendRequestDao friendRequestDao;
	
	public DefaultFriendRequestManager(FriendRequestDao friendRequestDao){
		this.friendRequestDao = friendRequestDao;
	}

	@Override
	public void releaseFriendRequest(FriendRequest newRequest) throws FriendRequestValidationException {
		System.out.println(newRequest.toString());
		System.out.println("Testing Friend Request instance if already exists");
		if(!newRequest.isNew()) {
	        throw new RuntimeException("FriendRequest already exists, use save method for updates!");
		} 
	        //newPost.validate();
		System.out.println("Testing Friend Request if already exists");    
	   /* Post existinCheck = postDao.findByPostId(newPost.getId());
	    if(existinCheck != null) {
	    	throw new PostValidationException("Post already exists!");
	    }*/
	    
	    System.out.println("Starting Friend Request Transaction");
	    friendRequestDao.startTransaction();   
        try {
        	friendRequestDao.save(newRequest);
            //newPost.setPoster(userDao.findById(posterId));
        } catch (Exception e) {
        	friendRequestDao.rollbackTransaction();
        }
        friendRequestDao.commitTransaction();
        System.out.println("Friend Request Transaction Complete");
	}
	
	@Override
	public void acceptFriendRequest(Long sourceId, Long targetId){
		System.out.println("Starting Friend Request Transaction");
		friendRequestDao.startTransaction();
		try {
        	friendRequestDao.acceptFriendRequest(sourceId, targetId);
            //newPost.setPoster(userDao.findById(posterId));
        } catch (Exception e) {
        	friendRequestDao.rollbackTransaction();
        	System.out.println(e);
        }
        friendRequestDao.commitTransaction();
        System.out.println("Friend Request Transaction Complete");
	}
	
	@Override
	public void declineFriendRequest(Long sourceId, Long targetId){
		System.out.println("Starting Friend Request Transaction");
		friendRequestDao.startTransaction();
		try {
        	friendRequestDao.declineFriendRequest(sourceId, targetId);
            //newPost.setPoster(userDao.findById(posterId));
        } catch (Exception e) {
        	friendRequestDao.rollbackTransaction();
        	System.out.println(e);
        }
        friendRequestDao.commitTransaction();
        System.out.println("Friend Request Transaction Complete");
	}
}
