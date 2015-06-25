package com.agilemaster.findoil.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="_comment")
public class Comment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8549677285255014726L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@ManyToOne()
	private User author;
	@Column
	private int sendSpeed;
	@Column
	private int productQuality;
	@Column
	private String star;
	@Column(columnDefinition = "longtext")
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar dateCreated;
	@OneToMany()
	private List<FileInfo> images;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Calendar getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}
	public int getSendSpeed() {
		return sendSpeed;
	}
	public void setSendSpeed(int sendSpeed) {
		this.sendSpeed = sendSpeed;
	}
	public int getProductQuality() {
		return productQuality;
	}
	public void setProductQuality(int productQuality) {
		this.productQuality = productQuality;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public List<FileInfo> getImages() {
		return images;
	}
	public void setImages(List<FileInfo> images) {
		this.images = images;
	}
	
}
