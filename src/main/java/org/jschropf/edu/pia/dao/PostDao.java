package org.jschropf.edu.pia.dao;

import java.util.Date;
import java.util.List;

import org.jschropf.edu.pia.domain.Post;

public interface PostDao extends GenericDao<Post>{
	/**
	 * 
	 * @param id the id of desired post
	 * @return post if found, otherwise null
	 */
	public Post findByPostId(long id);
	/**
	 * create post
	 * 
	 * @param title the title of post
	 * @param text the text of post
	 * @param posterId the id of poster
	 * @param ownerId the id of owner of wall
	 * @return post if created, otherwise null
	 */
	public Post createPost(String title, String text, long posterId, long ownerId);
	/**
	 * post on specific wall
	 * 
	 * @param personId the id of wall owner
	 * @return list of post for the wall owner
	 */
	public List<Post> wallFor(Long personId);
	/**
	 * update post id, not used anymore
	 * 
	 * @param posterId the id of poster
	 * @param postId the id of post
	 */
	public void updatePostId(Long posterId, Long postId);
	/**
	 * 
	 * @param date the date of posting
	 * @return first post with desired date or null
	 */
	public Post findByDate(Date date);
	/**
	 * 
	 * @param text the text of post
	 * @param title the title of post
	 * @return post with text and title or null
	 */
	public Post findByTexts(String text, String title);
	/**
	 * 
	 * @param personId the id of user
	 * @return list of ten posts for user
	 */
	public List<Post> topTenFor(Long personId); 
}
