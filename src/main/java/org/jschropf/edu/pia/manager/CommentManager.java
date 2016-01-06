package org.jschropf.edu.pia.manager;

import org.jschropf.edu.pia.domain.Comment;
import org.jschropf.edu.pia.domain.CommentValidationException;
import org.jschropf.edu.pia.domain.PostValidationException;

public interface CommentManager {

	void releaseComment(Comment newComment, Long posterId) throws CommentValidationException;

}
