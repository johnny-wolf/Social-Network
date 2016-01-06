package org.jschropf.edu.pia.dao;

import javax.persistence.EntityManager;

import org.jschropf.edu.pia.domain.BaseObject;
import org.springframework.stereotype.Repository;

/**
 * JPA implementation of the {@link GenericDao} interface.
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
@Repository
public class GenericDaoJpa<T extends BaseObject> implements GenericDao<T> {

    protected EntityManager em;
    private Class<T> persistedType;

    /**
     * @param em entity manager instance this DAO wraps
     * @param persistedType type of the entity persisted by this DAO
     */
    public GenericDaoJpa(EntityManager em, Class<T> persistedType) {
        this.em = em;
        this.persistedType = persistedType;
    }

    @Override
    public T save(T value) {
        if(value.isNew()) {
            em.persist(value);
            return value;
        } else {
            return em.merge(value);
        }
    }

    @Override
    public T findOne(Long id) {
        return em.find(persistedType, id);
    }

    @Override
    public void remove(T toRemove) {
        if(!toRemove.isNew()) {
            em.remove(toRemove);
        }
    }

    @Override
    public void startTransaction() {
        em.getTransaction().begin();
    }

    @Override
    public void commitTransaction() {
        em.getTransaction().commit();
    }

    @Override
    public void rollbackTransaction() {
        em.getTransaction().rollback();
    }
}
