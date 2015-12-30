package org.jschropf.edu.pia.dao;

import org.jschropf.edu.pia.domain.User;

/**
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public interface UserDao extends GenericDao<User> {

    /**
     *
     * @param username the requested username
     * @return user with the given username or null
     */
    User findByUsername(String username);

	User findById(long userId);

}
