package org.jschropf.edu.pia.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

/**
 * Picture, not used
 * 
 * @author Jan Schropfer
 *
 */
@Entity
@Table(name = "jschropf_SM_pictures")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Picture.findAll", query = "SELECT p FROM Picture p"),
    @NamedQuery(name = "Picture.findById", query = "SELECT p FROM Picture p WHERE p.id = :id"),
    @NamedQuery(name = "Picture.findByImage", query = "SELECT p FROM Picture p WHERE p.image = :image")}) 
public class Picture extends BaseObject{
	
	@Column(name = "image") 
	private String image;

	public Picture(){
	}

    //Getters and setters
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Picture{");
        //sb.append("postId='").append(postId).append('\'');
        sb.append("\nimage='").append(image).append('\'');
        sb.append('}');
        return sb.toString();
    }
    
    @ManyToOne
    @JoinColumn (name="postId")
    private Post post;
	
    public Post getPost(){
        return post;
    }
    
    public void setPost(Post post){
        this.post= post;
    } 
}
