package com.agilemaster.form.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
/**
 * 租户，一个租户有多个表单。
 * @author asdtiang
 */
@Table(keyspace = "junjie_form",name = "FormSaas")
public class FormSaas implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6246700292930185379L;
	@PartitionKey
	private String  id;
	private List<String> formList;
	private String accessKey;
	private Map<String,String> attributes;
	 @Column
	private Date dateCreated;
	 @Column
	private Date lastUpdated;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public List<String> getFormList() {
		return formList;
	}
	public void setFormList(List<String> formList) {
		this.formList = formList;
	}
	public String getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
}
