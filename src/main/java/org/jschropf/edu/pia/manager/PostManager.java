package org.jschropf.edu.pia.manager;

import java.util.List;

import org.jschropf.edu.pia.domain.Post;
import org.jschropf.edu.pia.domain.PostValidationException;

public interface PostManager {
	
	/**
	 * Method for releasing new post on the wall.
	 * @param post	post to be released
	 */
	//void releasePost (Post newPost, Long posterId) throws PostValidationException;

	void releasePost(Long posterId, String title, String text, Long ownerId) throws PostValidationException;

	List<Post> wallFor(Long personId);

	List<Post> topTenFor(Long personId); 
}
