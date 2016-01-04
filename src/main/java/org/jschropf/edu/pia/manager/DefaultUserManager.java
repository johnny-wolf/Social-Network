package org.jschropf.edu.pia.manager;

import org.jschropf.edu.pia.dao.UserDao;
import org.jschropf.edu.pia.domain.User;
import org.jschropf.edu.pia.domain.UserValidationException;
import org.jschropf.edu.pia.utils.Encoder;

/**
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public class DefaultUserManager implements UserManager {

    private UserDao userDao;
    private Encoder encoder;

    public DefaultUserManager(UserDao userDao, Encoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }
    
    @Override
    public boolean authenticate(String username, String password) {
        User u = userDao.findByUsername(username);
        System.out.println(u);
        return u != null && encoder.validate(password, u.getPassword());
    }

    @Override
    public void register(User newUser) throws UserValidationException {
        if(!newUser.isNew()) {
            throw new RuntimeException("User already exists, use save method for updates!");
        }

        newUser.validate();

        User existinCheck = userDao.findByUsername(newUser.getUsername());
        if(existinCheck != null) {
            throw new UserValidationException("Username already taken!");
        }

        newUser.setPassword(encoder.encode(newUser.getPassword()));

        userDao.startTransaction();
        try {
            userDao.save(newUser);
        } catch (Exception e) {
            userDao.rollbackTransaction();
        }
        userDao.commitTransaction();
    }
    
    @Override
    public long userIdFinder(String username){
    	User u = userDao.findByUsername(username);
    	System.out.println(u);
    	return u.getId();
    }
}
