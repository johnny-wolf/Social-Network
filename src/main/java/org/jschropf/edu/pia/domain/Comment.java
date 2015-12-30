package org.jschropf.edu.pia.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery; 

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "jschropf_SM_comments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c"),
    @NamedQuery(name = "Comment.findById", query = "SELECT c FROM Comment c WHERE c.id = :id"),
    @NamedQuery(name = "Comment.findByText", query = "SELECT c FROM Comment c WHERE c.text = :text"),
    @NamedQuery(name = "Comment.findByDate", query = "SELECT c FROM Comment c WHERE c.date = :date"),
    //@NamedQuery(name = "Comment.findByPersonId", query = "SELECT c FROM Comment c WHERE c.personId = :personId"),
    @NamedQuery(name = "Comment.findByPostId", query = "SELECT c FROM Comment c WHERE c.postId = :postId")}) 
public class Comment extends BaseObject{

	@Column(name = "postId") 
	private long postId;
	@Column(name = "personId") 
	private long personId;
	@Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP) 
	private Date date;
	@Column(name = "text") 
	private String text;
	
	public Comment() {
    }
	
	/*
    ########### MAPPINGS #####################
     */
	
	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	} 
	
	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("person id='").append(personId).append('\'');
        sb.append("\ndate='").append(date).append('\'');
        sb.append("\ntext='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
