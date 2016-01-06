package org.jschropf.edu.pia.dao;

import java.util.List;

import org.jschropf.edu.pia.domain.Comment;

public interface CommentDao extends GenericDao<Comment>{
	public Comment findByCommentId(long id);

	public Comment createComment(String text, Long personId, Long postId);

	public List<Comment> commentsFor(Long postId);

}
