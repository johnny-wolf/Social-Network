package org.jschropf.edu.pia.domain;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "jschropf_SM_posts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p"),
    @NamedQuery(name = "Post.findById", query = "SELECT p FROM Post p WHERE p.id = :id"),
    @NamedQuery(name = "Post.findByTitle", query = "SELECT p FROM Post p WHERE p.title = :title"),
    @NamedQuery(name = "Post.findByDate", query = "SELECT p FROM Post p WHERE p.date = :date"),
    @NamedQuery(name = "Post.findByText", query = "SELECT p FROM Post p WHERE p.text = :text"),
    //@NamedQuery(name = "Post.findByPosterId", query = "SELECT p FROM Post p WHERE p.posterId = :posterId"),
    @NamedQuery(name = "Post.findByOwnerId", query = "SELECT p FROM Post p WHERE p.ownerId = :ownerId"),
    @NamedQuery(name = "Post.findByPopularity", query = "SELECT p FROM Post p WHERE p.popularity = :popularity")
}) 
public class Post extends BaseObject{
	
	@Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP) 
	private Date date;
	
    @Column(name = "text") 
	private String text;
    
    @Column(name = "title")
	private String title;
    
    @Basic(optional = false)
	private long ownerId;
    
    @Column(name = "popularity") 
	private int popularity;

    public Post(){}
	public Post(String title,String text, Long posterId, Long ownerId, int popularity, Date date) {
		this.date = date;
		this.ownerId = ownerId;
		this.text = text;
		this.title = title;
    }
	
	public Post(String title, String text, Long posterId,Long ownerId) {
		this(title, text, posterId, ownerId, 0, new Date()); 
	}
	
	/*
    ########### MAPPINGS #####################
     */
	
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

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getFormattedDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
	return formatter.format(date);
    } 
	
	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Post{");
        sb.append("post id='").append(getId()).append('\'');
        sb.append("owner id='").append(ownerId).append('\'');
        sb.append("\ndate='").append(date).append('\'');
        sb.append("\ntext='").append(text).append('\'');
        sb.append("\ntitle='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }
	
	/*@OneToMany (targetEntity=Picture.class, mappedBy="post", cascade=CascadeType.PERSIST)
    private Set<Picture> pictures = new HashSet<Picture>();

    public Set<Picture> getPictures() {
    	
	  return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
    	
	   this.pictures = pictures;
    }*/
	
    @ManyToOne
    @JoinColumn (name="posterId")
    private User poster;
    
    public User getPoster(){
        return poster;
    }
    
    public void setPoster(User poster){
        this.poster = poster;
    } 
}
