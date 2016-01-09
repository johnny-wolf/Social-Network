package org.jschropf.edu.pia.manager;

import java.util.List;

import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.domain.UserValidationException;

/**
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public interface UserManager {

    /**
     * Method for authentication of user's credentials.
     *
     * @param username provided login
     * @param password provided password
     * @return true if username and password are a match, false otherwise
     */
    boolean authenticate(String username, String password);

    /**
     * Method for registering a new user.
     * @param newUser instance with new user data, expected not-null value
     * @throws UserValidationException if the new user data instance is not in valid state,
     *                                 e.g. required fields are missing
     */
    void register(User newUser) throws UserValidationException;

    /**
     * 
     * @param username
     * @return
     */
    public long userIdFinder(String username);

	public void updatePicture(User user, String filename);

	List<User> findAllSortedByDateOfBirth(boolean order);

	List<User> findAllSortedByName(boolean order);

	User findByUsername(String username);

	List<User> friendsSortedByDateOfBirth(Long personId, boolean order);

	List<User> friendsSortedByName(Long personId, boolean order);

	List<User> nonFriendsFor(Long personId);

	List<User> unansweredFriendRequestsFor(Long personId);
}
