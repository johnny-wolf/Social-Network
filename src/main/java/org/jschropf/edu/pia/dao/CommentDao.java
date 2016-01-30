package org.jschropf.edu.pia.dao;

import java.util.List;

import org.jschropf.edu.pia.domain.Comment;

/**
 * 
 * @author Jan Schropfer
 *
 */
public interface CommentDao extends GenericDao<Comment>{
	/**
	 * request for finding comment by id
	 * 
	 * @param id the requested Comment
	 * @return comment with given id or null
	 */
	public Comment findByCommentId(long id);

	/**
	 * request for creating comment
	 * 
	 * @param text the text inside comment
	 * @param personId the id of commenter
	 * @param postId the id of post
	 * @return created comment
	 */
	public void createComment(String text, Long personId, Long postId);

	/**
	 * request for comments of one post
	 * 
	 * @param postId the id of post
	 * @return list of comments for post
	 */
	public List<Comment> commentsFor(Long postId);

}
