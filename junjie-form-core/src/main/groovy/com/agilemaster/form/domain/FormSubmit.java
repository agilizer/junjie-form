package com.agilemaster.form.domain;

import java.io.Serializable;
import java.util.Date;

import com.agilemaster.form.constants.JunjieFormConstants;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * @author asdtiang
 */
@Table (keyspace = JunjieFormConstants.DEFAULT_KEY_SPACE, name = "FormSubmit")
public class FormSubmit   implements Serializable{
	private static final long serialVersionUID = -5497177824207251105L;
	@PartitionKey
	private String id;
	private String formId;
	private String userId;
	private String name;
	private Date dateCreated;
	private boolean finish; 
	private int answerCount;
	private int expectCount;
	private Date startTime;
	private Date endTime;
	private long answerTime;
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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
	public long getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(long answerTime) {
		this.answerTime = answerTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public boolean isFinish() {
		return finish;
	}
	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
	
}
