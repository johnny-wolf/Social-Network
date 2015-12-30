package org.jschropf.edu.pia.dao;

import org.jschropf.edu.pia.domain.Comment;

public interface CommentDao extends GenericDao<Comment>{
	Comment findByCommentId(long id);

}
