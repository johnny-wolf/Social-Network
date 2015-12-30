package org.jschropf.edu.pia;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.jschropf.edu.pia.dao.UserDao;
import org.jschropf.edu.pia.dao.UserDaoJpa;
import org.jschropf.edu.pia.manager.DefaultUserManager;
import org.jschropf.edu.pia.manager.UserManager;
import org.jschropf.edu.pia.utils.Encoder;
import org.jschropf.edu.pia.utils.PasswordHashEncoder;
import org.jschropf.edu.pia.web.auth.AuthenticationService;

/**
 * Application context holds references to all parts of the application,
 * manages their creation and provides them wherever needed.
 *
 * TODO currently are all instances held by the context hard-coded.
 * TODO the whole mechanism could be made dynamic using String ids to identify each held instance
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
public class ApplicationContext {

    //persistence
    private EntityManager em;
    private UserDao userDao;

    //business
    private UserManager userManager;
    private Encoder encoder;

    //web
    private AuthenticationService authenticationService;

    public ApplicationContext() {
        //TODO persistence unit name should be taken from a property file, not hard-coded!
        em = Persistence.createEntityManagerFactory("org.danekja.edu.pia").createEntityManager();
        userDao = new UserDaoJpa(em);
        encoder = new PasswordHashEncoder();
        userManager = new DefaultUserManager(userDao, encoder);
        authenticationService = new AuthenticationService(userManager);
    }

    public void destroy() {
        em.close();
    }

    public EntityManager getEm() {
        return em;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public Encoder getEncoder() {
        return encoder;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }
}
