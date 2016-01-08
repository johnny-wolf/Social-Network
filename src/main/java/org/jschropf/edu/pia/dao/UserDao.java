package org.jschropf.edu.pia.dao;

import java.util.List;

import org.jschropf.edu.pia.domain.User;

/**
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public interface UserDao extends GenericDao<User> {

    /**
     *
     * @param username the requested username
     * @return user with the given username or null
     */
    public User findByUsername(String username);

	public User findById(long userId);

	public List<User> unansweredFriendRequestsFor(Long personId);

	List<User> friendsFor(Long personId);

	List<User> friendsSortedByName(Long personId, boolean isAscending);

	List<User> friendsSortedByDateOfBirth(Long personId, boolean isAscending);

	List<User> nonFriendsFor(Long personId);

	boolean updatePicture(Long personId, String filename);

	List<User> findAllSortedByDateOfBirth(boolean isAscending);

	List<User> findAllSortedByName(boolean isAscending);

}
