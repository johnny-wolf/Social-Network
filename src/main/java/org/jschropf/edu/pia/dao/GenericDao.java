package org.jschropf.edu.pia.dao;

import org.jschropf.edu.pia.domain.BaseObject;

/**
 * Common interface for all DAOs
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public interface GenericDao<T extends BaseObject> {

    T save(T value);

    T findOne(Long id);

    void remove(T toRemove);

    /*
        Transaction handling, very crude, there are better ways to do this.
     */

    void startTransaction();

    void commitTransaction();

    void rollbackTransaction();
}
