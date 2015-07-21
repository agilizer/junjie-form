package com.agilemaster.form.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户回答的表单记录
 * @author asdtiang
 *
 */
//@Table(keyspace = "junjie_form", name = "FormUser")
public class FormUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3642006078357814461L;
	//@PartitionKey
	private String id;
	// @Frozen("list<frozen<FormListShow>>")
	private List<FormListShow> formList;
	private Date dateCreated;
	private Date lastUpdated;
	
	
	public List<FormListShow> getFormList() {
		return formList;
	}
	public void setFormList(List<FormListShow> formList) {
		this.formList = formList;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
