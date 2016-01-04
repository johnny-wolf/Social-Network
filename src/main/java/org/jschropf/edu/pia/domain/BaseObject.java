package org.jschropf.edu.pia.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Date: 26.11.15
 *
 * @author Jakub Danek
 */
@MappedSuperclass
public class BaseObject {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id") 
    private Long id;

    /**
     *
     * @return true if the entity hasn't been persisted yet
     */
    @Transient
    public boolean isNew() {
        return id == null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
