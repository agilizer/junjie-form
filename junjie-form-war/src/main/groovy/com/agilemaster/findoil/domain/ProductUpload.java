package com.agilemaster.findoil.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table()
public class ProductUpload implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2480136541324063723L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@Column
	private ProductUploadType uploadType;
	@ManyToOne()
	private User user;
	@Column(columnDefinition = "longtext")
	private String textContent;
	@OneToOne()
	private FileInfo fileInfo;
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar dateCreated;

	public static enum ProductUploadType{
		FILE,
		TEXT
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductUploadType getUploadType() {
		return uploadType;
	}

	public void setUploadType(ProductUploadType uploadType) {
		this.uploadType = uploadType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public Calendar getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	
	
}
