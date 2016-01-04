package org.jschropf.edu.pia.manager;

import org.jschropf.edu.pia.domain.Post;
import org.jschropf.edu.pia.domain.PostValidationException;

public interface PostManager {
	
	/**
	 * Method for releasing new post on the wall.
	 * @param post	post to be released
	 */
	void releasePost (Post newPost) throws PostValidationException; 
}
