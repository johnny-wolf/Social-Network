package org.jschropf.edu.pia.manager;

import java.util.List;

import org.jschropf.edu.pia.domain.Comment;
import org.jschropf.edu.pia.domain.CommentValidationException;
import org.jschropf.edu.pia.domain.PostValidationException;

public interface CommentManager {

	void releaseComment(Long posterId, String text, Long postId) throws CommentValidationException;

	List<Comment> commentsFor(Long postId);

}
