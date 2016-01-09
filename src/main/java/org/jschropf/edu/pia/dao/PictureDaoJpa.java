package org.jschropf.edu.pia.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.jschropf.edu.pia.domain.Picture;
import org.springframework.stereotype.Repository;

@Repository
public class PictureDaoJpa extends GenericDaoJpa<Picture> implements PictureDao{
	
	public PictureDaoJpa() {
        super(Picture.class);
    }

    @Override
    public Picture findByPictureId(long id) {
        TypedQuery<Picture> q = em.createQuery("SELECT p FROM Picture p WHERE p.id = :pid", Picture.class);
        q.setParameter("pid", id);
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            //no result found, ensuring the behaviour described by interface specification
            //see javadoc of the findByUsername method.
            return null;
        }
    }
}
