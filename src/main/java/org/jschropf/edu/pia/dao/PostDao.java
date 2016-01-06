package org.jschropf.edu.pia.dao;

import java.util.Date;
import java.util.List;

import org.jschropf.edu.pia.domain.Post;

public interface PostDao extends GenericDao<Post>{
	public Post findByPostId(long id);
	public Post createPost(String title, String text, long posterId, long ownerId);
	public java.util.List<Post> wallFor(Long personId);
	public void updatePostId(Long posterId, Long postId);
	public Post findByDate(Date date);
	public Post findByTexts(String text, String title);
	public List<Post> topTenFor(Long personId); 
}
