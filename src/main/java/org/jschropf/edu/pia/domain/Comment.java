package org.jschropf.edu.pia.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery; 

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * Comment
 * 
 * @author Jan Schropfer
 *
 */
@Entity
@Table(name = "jschropf_SM_comments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c"),
    @NamedQuery(name = "Comment.findById", query = "SELECT c FROM Comment c WHERE c.id = :id"),
    @NamedQuery(name = "Comment.findByText", query = "SELECT c FROM Comment c WHERE c.text = :text"),
    @NamedQuery(name = "Comment.findByDate", query = "SELECT c FROM Comment c WHERE c.date = :date"),
    @NamedQuery(name = "Comment.findByPostId", query = "SELECT c FROM Comment c WHERE c.postId = :postId")}) 
public class Comment extends BaseObject{

	//Post id
	@Column(name = "postId") 
	private long postId;
	
	// Date of posting
	@Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP) 
	private Date date;
	
	//comment text
	@Column(name = "text") 
	private String text;
	
	//Constructor
	public Comment() {
    }
	
	// Getters and Setters
	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
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
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    } 
	
	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("person id='").append(commenter.getId()).append('\'');
        sb.append("\ndate='").append(date).append('\'');
        sb.append("\ntext='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
	
	//foreign key person id
	@ManyToOne
	@JoinColumn (name="personId")
	private User commenter;
	    
	public User getCommenter(){
		return commenter;
	}
	    
	public void setCommenter(User commenter){
		this.commenter = commenter;
	} 
}
