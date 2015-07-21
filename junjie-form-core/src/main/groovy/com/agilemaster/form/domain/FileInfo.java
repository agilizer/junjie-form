package com.agilemaster.form.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.datastax.driver.mapping.EnumType;
import com.datastax.driver.mapping.annotations.Enumerated;
import com.datastax.driver.mapping.annotations.UDT;
@UDT (keyspace = "junjie_form", name = "FileInfo")
public class FileInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5125176334336764053L;
	private String  id;
	private String  authorId;
	/**
	 * include path and name';
	 */
	private String storePath;
	private String description;
	private Long fileSize;
	@Enumerated(EnumType.STRING)
	private FileType fileType;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * 
	 */
	private String originalName;
	private String storeFileName;
	private Map<String,String> otherInfo;
	
	private    Date dateCreated;
	private    Date lastUpdated;
	
	public static enum FileType{
		EXCEL,
		WORD,
		TEXT,
		PDF,
		IMG
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public void setOtherInfo(Map<String, String> otherInfo) {
		this.otherInfo = otherInfo;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
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
	
	public String getStoreFileName() {
		return storeFileName;
	}
	public void setStoreFileName(String storeFileName) {
		this.storeFileName = storeFileName;
	}
	public Map<String, String> getOtherInfo() {
		return otherInfo;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}