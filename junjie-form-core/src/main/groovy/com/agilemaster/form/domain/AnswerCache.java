package com.agilemaster.form.domain;

import java.io.Serializable;
import java.util.Date;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "junjie_form",name = "AnswerCache")
public class AnswerCache  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3366606186370601405L;

	/**
	 * md5(saasId+formId+answerId);
	 */
	@PartitionKey
	private String id;
	/**
	 * formrender answer cache
	 */
	private String formRenderCache;
	private boolean finish;
	private Date startAnswerTime;
	private Date endAnswerTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFormRenderCache() {
		return formRenderCache;
	}
	public void setFormRenderCache(String formRenderCache) {
		this.formRenderCache = formRenderCache;
	}
	public boolean isFinish() {
		return finish;
	}
	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	public Date getStartAnswerTime() {
		return startAnswerTime;
	}
	public void setStartAnswerTime(Date startAnswerTime) {
		this.startAnswerTime = startAnswerTime;
	}
	public Date getEndAnswerTime() {
		return endAnswerTime;
	}
	public void setEndAnswerTime(Date endAnswerTime) {
		this.endAnswerTime = endAnswerTime;
	}
}
