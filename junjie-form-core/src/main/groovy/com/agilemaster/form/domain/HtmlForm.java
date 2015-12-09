package com.agilemaster.form.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.agilemaster.form.constants.JunjieFormConstants;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
@Table(keyspace = JunjieFormConstants.DEFAULT_KEY_SPACE, name = "HtmlForm")
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
	 * 实现 {@link HtmlForm} 的链式关系，简单实现表单流程。
	 */
	private String childrenFormId;
	/**
	 * 前端传入的整个form json字符串。
	 */
	private String jsonContent;
	/**
	 * 表单的名称(必填)
	 */
	private String name;
	
	private String description;
	
	private Map<String,String> customInfo;
	private boolean finish; 
	private int answerCount;
	private int expectCount;
	/**
	 * 是否完全回答正确再进入下一个表单。
	 */
	private boolean allRightNext = false;
	/**
	 * htmlInput 计数
	 */
	private int inputCount;
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
	public int getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
	public int getExpectCount() {
		return expectCount;
	}
	public void setExpectCount(int expectCount) {
		this.expectCount = expectCount;
	}
	public String getJsonContent() {
		return jsonContent;
	}
	public void setJsonContent(String jsonContent) {
		this.jsonContent = jsonContent;
	}
	public int getInputCount() {
		return inputCount;
	}
	public void setInputCount(int inputCount) {
		this.inputCount = inputCount;
	}
	public String getChildrenFormId() {
		return childrenFormId;
	}
	public void setChildrenFormId(String childrenFormId) {
		this.childrenFormId = childrenFormId;
	}
	public boolean isAllRightNext() {
		return allRightNext;
	}
	public void setAllRightNext(boolean allRightNext) {
		this.allRightNext = allRightNext;
	}
	
	
}
