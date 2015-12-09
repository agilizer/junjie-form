package com.agilemaster.form.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.agilemaster.form.constants.JunjieFormConstants;
import com.datastax.driver.mapping.EnumType;
import com.datastax.driver.mapping.annotations.Enumerated;
import com.datastax.driver.mapping.annotations.UDT;
@UDT (keyspace =JunjieFormConstants.DEFAULT_KEY_SPACE, name = "FileInfo")
public class FileInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5125176334336764053L;
	private String  id;
	private String  saasId;
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
	private Map<String,String> attributes;
	private    Date dateCreated;
	public static enum FileType{
		EXCEL,
		WORD,
		TEXT,
		PDF,
		IMG,
		OTHERS
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getSaasId() {
		return saasId;
	}
	public void setSaasId(String saasId) {
		this.saasId = saasId;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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
	public Date getDateCreated() {
		return dateCreated;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	

}
