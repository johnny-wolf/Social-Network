package org.jschropf.edu.pia.dao;

import org.jschropf.edu.pia.domain.Picture;

/**
 * 
 * @author Jan Schropfer
 *
 */
public interface PictureDao extends GenericDao<Picture>{
	/**
	 * 
	 * @param id the id of desired picture
	 * @return picture if found, otherwise null
	 */
	Picture findByPictureId(long id);
	
}
