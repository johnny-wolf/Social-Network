package org.jschropf.edu.pia.manager;

import java.util.List;

import org.jschropf.edu.pia.domain.Post;
import org.jschropf.edu.pia.domain.PostValidationException;

public interface PostManager {

	/**
	 * Method for releasing new post
	 * 
	 * @param posterId id of poster
	 * @param title title of post
	 * @param text text of post
	 * @param ownerId owner of post
	 * @throws PostValidationException
	 */
	void releasePost(Long posterId, String title, String text, Long ownerId) throws PostValidationException;

	/**
	 * Method for getting post on specific wall
	 * @param personId wall owners id
	 * @return list of post for specific wall
	 */
	List<Post> wallFor(Long personId);

	/**
	 * Method for getting top ten for user
	 * 
	 * @param personId owner of wall id
	 * @return List of top ten post for specific wall
	 */
	List<Post> topTenFor(Long personId); 
}
