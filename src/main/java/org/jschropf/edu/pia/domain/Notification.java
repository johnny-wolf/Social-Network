package org.jschropf.edu.pia.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "jschropf_SM_notifications")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n"),
    @NamedQuery(name = "Notification.findById", query = "SELECT n FROM Notification n WHERE n.id = :id"),
    @NamedQuery(name = "Notification.findByText", query = "SELECT n FROM Notification n WHERE n.text = :text"),
    @NamedQuery(name = "Notification.findByUrl", query = "SELECT n FROM Notification n WHERE n.url = :url"),
    @NamedQuery(name = "Notification.findByDate", query = "SELECT n FROM Notification n WHERE n.date = :date"),
    @NamedQuery(name = "Notification.findByPersonId", query = "SELECT n FROM Notification n WHERE n.personId = :personId")}) 
public class Notification extends BaseObject{

	@Column(name = "personId") 
	private long personId;
	@Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP) 
	private Date date;
	@Column(name = "text") 
	private String text;
	@Column(name = "url") 
	private String url;
	
	public Notification(){
		
	}
	
	public Notification( String text, long personId) {
        this.text = text;
        this.personId = personId;
    }

	/*
    ########### MAPPINGS #####################
     */
	
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
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Notification{");
        sb.append("person id='").append(personId).append('\'');
        sb.append("\ndate='").append(date).append('\'');
        sb.append("\ntext='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
