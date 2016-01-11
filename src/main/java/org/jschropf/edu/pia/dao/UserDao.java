package org.jschropf.edu.pia.dao;

import java.util.Date;
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
    
    /**
     * 
     * @param userId the requested userId
     * @return user with the given userId or null
     */
	public User findById(long userId);

	/**
	 * 
	 * @param personId the id of user
	 * @return list of users requesting friendship
	 */
	public List<User> unansweredFriendRequestsFor(Long personId);

	/**
	 * 
	 * @param personId the person id
	 * @return list of friends
	 */
	List<User> friendsFor(Long personId);

	/**
	 * 
	 * @param personId the person id
	 * @param isAscending the boolean if is ascending
	 * @return ascending or descending list of friends by their names
	 */
	List<User> friendsSortedByName(Long personId, boolean isAscending);

	/**
	 * 
	 * @param personId the person id
	 * @param isAscending the boolean if is ascending
	 * @return ascending or descending list of friends by their date of birth
	 */
	List<User> friendsSortedByDateOfBirth(Long personId, boolean isAscending);

	/**
	 * 
	 * @param personId the person id
	 * @return list of users who are not friends with user
	 */
	List<User> nonFriendsFor(Long personId);

	/**
	 * 
	 * @param personId the person id
	 * @param filename the name of picture
	 * @return true if updated otherwise false
	 */
	boolean updatePicture(Long personId, String filename);
	
	/**
	 * 
	 * @param isAscending the boolean if ascending or not
	 * @return list of users sorted by date of birth
	 */
	List<User> findAllSortedByDateOfBirth(boolean isAscending);

	/**
	 * 
	 * @param isAscending the boolean if ascending or not
	 * @return list of users sorted by name
	 */
	List<User> findAllSortedByName(boolean isAscending);

	/**
	 * 
	 * @param personId the person id
	 * @param firstName the first name of person
	 * @param lastName the last name of person
	 * @param dayOfBirth the day of birth
	 * @param monthOfBirth the month of birth
	 * @param yearOfBirth the year of birth
	 * @return true if successfully updated, otherwise null
	 */
	boolean updatePersonalInformation(Long personId, String firstName, String lastName, String dayOfBirth,
			String monthOfBirth, String yearOfBirth);

	/**
	 * 
	 * @param userId id of user
	 * @param password hashed password
	 */
	void updatePassword(Long userId, String password);

	/**
	 * 
	 * @param personId the person id
	 * @param firstName the first name of person
	 * @param lastName the last name of person
	 * @param date the birth date
	 * @return true if successfully updated, otherwise null
	 */
	boolean updatePersonalInformation(Long personId, String firstName, String lastName, Date date);

}
