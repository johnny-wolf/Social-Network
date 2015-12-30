package org.jschropf.edu.pia.dao;

import org.jschropf.edu.pia.domain.Picture;

public interface PictureDao extends GenericDao<Picture>{
	Picture findByPictureId(long id);
	
}
