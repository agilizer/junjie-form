package com.agilemaster.form.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
@Table(keyspace = "junjie_form", name = "HtmlForm")
public class HtmlForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 440632749764772923L;	
	@PartitionKey
	private String id;
	private String saasId;
	private String beforeText;
	private String afterText;
	/**
	 * 表单的名称(必填)
	 */
	private String name;
	
	private String description;
	
	private Map<String,String> customInfo;
	private boolean finish; 
	private long answerCount;
	private long expectCount;
	private Date startTime;
	private Date endTime;
	private Date dateCreated;
	private Date lastUpdated;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSaasId() {
		return saasId;
	}
	public void setSaasId(String saasId) {
		this.saasId = saasId;
	}
	public Map<String, String> getCustomInfo() {
		return customInfo;
	}
	public void setCustomInfo(Map<String, String> customInfo) {
		this.customInfo = customInfo;
	}
	public String getBeforeText() {
		return beforeText;
	}
	public void setBeforeText(String beforeText) {
		this.beforeText = beforeText;
	}
	public String getAfterText() {
		return afterText;
	}
	public void setAfterText(String afterText) {
		this.afterText = afterText;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public boolean isFinish() {
		return finish;
	}
	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	public long getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(long answerCount) {
		this.answerCount = answerCount;
	}
	public long getExpectCount() {
		return expectCount;
	}
	public void setExpectCount(long expectCount) {
		this.expectCount = expectCount;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
