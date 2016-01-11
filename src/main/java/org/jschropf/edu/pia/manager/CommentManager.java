package org.jschropf.edu.pia.manager;

import java.util.List;

import org.jschropf.edu.pia.domain.Comment;
import org.jschropf.edu.pia.domain.CommentValidationException;
import org.jschropf.edu.pia.domain.PostValidationException;

/**
 * Manager for comments
 * 
 * @author Jan Schropfer
 *
 */
public interface CommentManager {

	/**
	 * Method for creating comments
	 * 
	 * @param posterId id of poster of comment
	 * @param text comment text
	 * @param postId post id
	 * @throws CommentValidationException
	 */
	void releaseComment(Long posterId, String text, Long postId) throws CommentValidationException;

	/**
	 * Method for getting comments for specific post
	 * 
	 * @param postId id of post
	 * @return List of comments for specific post
	 */
	List<Comment> commentsFor(Long postId);

}
