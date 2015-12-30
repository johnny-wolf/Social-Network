package org.jschropf.edu.pia.dao;

import org.jschropf.edu.pia.domain.Post;

public interface PostDao extends GenericDao<Post>{
	Post findByPostId(long id);
	public boolean createPost(String title, String text, long posterId, long ownerId, String picture);
	public java.util.List<Post> wallFor(java.lang.Integer personId); 
}
