package org.jschropf.edu.pia.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

/**
 * Entity representing application User.
 *
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
@Entity
@Table(name = "jschropf_SM_users")
public class User extends BaseObject {
    /**
     * Login, unique
     */
	//@Column(name = "username")
    private String username;
    /**
     * Secret for signing-in
     */
    //@Column(name = "password") 
    private String password;

    //@Column(name = "firstName") 
    private String fName;
    
    //@Column(name = "lastName") 
    private String lName;
    /**
     * Profile picture
     */
    private Picture picture;

    public User() {
    }
    
    public User(String username, String password, Picture picture, String fName, String lName){
    	this.username = username;
        this.password = password;
        this.fName = fName;
        this.lName = lName;
        this.picture = picture;
    }
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /*
    ########### API ##################
     */

    /**
     * Validates that user instance is currently in a valid state.
     * @throws UserValidationException in case the instance is not in valid state.
     */
    public void validate() throws UserValidationException {
        if(StringUtils.isBlank(username)) throw new UserValidationException("Username is a required field");
        if(StringUtils.isBlank(password)) throw new UserValidationException("Password is a required field");
    }

    /*
    ########### MAPPINGS #####################
     */

    @Column(unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return !(username != null ? !username.equals(user.username) : user.username != null);

    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("username='").append(username).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
