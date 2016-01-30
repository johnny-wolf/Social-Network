package org.jschropf.edu.pia.manager;

import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.domain.UserValidationException;

/**
 * Date: 26.11.15
 *
 * @author Jakub Danek, Jan Schropfer
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
     * Method for finding user id by user name
     * 
     * @param username user name
     * @return user id
     */
    public long userIdFinder(String username);

    /**
     * Method for updating picture location
     * 
     * @param user owner of picture
     * @param filename file path
     */
	public void updatePicture(User user, String filename);

	/**
	 * Method for getting all users sorted by date of birth
	 * 
	 * @param order order of users
	 * @return sorted list of Users
	 */
	public List<User> findAllSortedByDateOfBirth(boolean order);

	/**
	 * Method for getting all users sorted by name
	 * 
	 * @param order order
	 * @return sorted List of Users
	 */
	List<User> findAllSortedByName(boolean order);

	/**
	 * Method for finding user by user name
	 * @param username user name
	 * @return User or null
	 */
	User findByUsername(String username);

	/**
	 * Method for getting all friends sorted by date of birth
	 * 
	 * @param order order of users
	 * @return sorted list of friends
	 */
	List<User> friendsSortedByDateOfBirth(Long personId, boolean order);

	/**
	 * Method for getting all users sorted by name
	 * 
	 * @param personId user id
	 * @return sorted list of friends
	 */
	List<User> friendsSortedByName(Long personId, boolean order);

	/**
	 * Method for getting all users who are not friends
	 * 
	 * @personId user id
	 * @return sorted list of users
	 */
	List<User> nonFriendsFor(Long personId);

	/**
	 * Method for finding unanswered friend requests
	 * @param personId user id
	 * @return list of users with friend requests being unanswered for this user
	 */
	List<User> unansweredFriendRequestsFor(Long personId);

	/**
	 * Method for updating personal informations
	 * 
	 * @param personId user id
	 * @param firstName first name
	 * @param lastName last name
	 * @param dayOfBirth day of birth
	 * @param monthOfBirth month of birth
	 * @param yearOfBirth year of birth
	 * @return true if succesfull, otherwise false
	 */
	boolean updatePersonalInformation(Long personId, String firstName, String lastName, String dayOfBirth,
			String monthOfBirth, String yearOfBirth);

	/**
	 * Method for updating hashed password
	 * 
	 * @param password non hashed password
	 * @param user user
	 */
	void updatePassword(String password, User user);

	/**
	 * Method for updating personal informations
	 * 
	 * @param personId user id
	 * @param firstName first name
	 * @param lastName last name
	 * @param date date of birth
	 * @return true if successful, otherwise false
	 */
	boolean updatePersonalInformation(Long personId, String firstName, String lastName, Date date);

	/**
	 * Method for finding user by his id
	 * 
	 * @param personId user id
	 * @return User or null
	 */
	User findById(Long personId);

	/**
	 * Method for uploading file
	 * 
	 * @param fileName name of file
	 * @param filePath path to file
	 * @param uploadFolder upload folder
	 * @param result user whose file it is
	 * @param picture item with picture
	 * @return
	 */
	boolean uploadFile(String fileName, String filePath, String uploadFolder, User result, FileItem picture);
}
