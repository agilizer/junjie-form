package com.agilemaster.findoil.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table()
public class FileInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5125176334336764053L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@ManyToOne()
	private User author;
	/**
	 * include path and name';
	 */
	@Column
	private String storePath;
	@Column
	private Long fileSize;
	@Column
	private FileType fileType;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * 
	 */
	@Column
	private String originalName;
	
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar dateCreated;
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar lastUpdated;
	
	public static enum FileType{
		EXCEL,
		WORD,
		TEXT,
		PDF,
		IMG
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStorePath() {
		return storePath;
	}
	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public Calendar getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Calendar getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public FileType getFileType() {
		return fileType;
	}
	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}

}
