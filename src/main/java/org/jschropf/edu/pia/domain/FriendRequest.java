package org.jschropf.edu.pia.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Friend Request
 * 
 * @author Jan Schropfer
 *
 */
@Entity
@Table(name = "jschropf_SM_friendRequests") 
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FriendRequest.findAll", query = "SELECT f FROM FriendRequest f"),
    @NamedQuery(name = "FriendRequest.findById", query = "SELECT f FROM FriendRequest f WHERE f.id = :id"),
    @NamedQuery(name = "FriendRequest.findByDate", query = "SELECT f FROM FriendRequest f WHERE f.date = :date"),
    @NamedQuery(name = "FriendRequest.findBySourceId", query = "SELECT f FROM FriendRequest f WHERE f.sourceId = :sourceId"),
    @NamedQuery(name = "FriendRequest.findByTargetId", query = "SELECT f FROM FriendRequest f WHERE f.targetId = :targetId")}) 
public class FriendRequest extends BaseObject{

	// Id of person who requested friendship
	@Column(name = "sourceId")
	private long sourceId;
	
	//Id of targeted person
	@Column(name = "targetId") 
	private long targetId;
	
	//Date of sending the request
	@Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP) 
	private Date date;
	
	//Status of request
	@Column(name = "status") 
	private String status;
	
	//Constructors
	public FriendRequest(){
	}
	
	public FriendRequest(long sourceId, long targetId){
		this.sourceId = sourceId;
		this.targetId = targetId;
	}


	//Getters and Setters	
	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getFormattedDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
	return formatter.format(date);
    } 
	
	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Friend Request{");
        sb.append("source id='").append(sourceId).append('\'');
        sb.append("\ntarget id='").append(targetId).append('\'');
        sb.append("\ndate='").append(date).append('\'');
        sb.append('}');
        return sb.toString();
    }
	
}
