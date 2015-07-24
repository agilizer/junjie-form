package com.agilemaster.form.domain;

import java.util.Date;
import java.util.List;

import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
@Table(keyspace = "junjie_form", name = "InputValue")
public class InputValue {
	@PartitionKey
	private String id;
	private String label;
	private String formSubmitId;
	private String userId;
	private String formId;
	private  String htmlInputId;
	private String strValue;
	private Long numberValue;
	private Double doubleValue;
	private Date dateValue;
	/**
	 * 多选或者多个input
	 */
	private List<String> listValue;
	 @Frozen("list<frozen<FileInfo>>")
	private List<FileInfo> fileInfoes;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getHtmlInputId() {
		return htmlInputId;
	}
	public void setHtmlInputId(String htmlInputId) {
		this.htmlInputId = htmlInputId;
	}
	public String getStrValue() {
		return strValue;
	}
	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}
	public Long getNumberValue() {
		return numberValue;
	}
	public void setNumberValue(Long numberValue) {
		this.numberValue = numberValue;
	}
	public Double getDoubleValue() {
		return doubleValue;
	}
	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}
	public Date getDateValue() {
		return dateValue;
	}
	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}
	public List<String> getListValue() {
		return listValue;
	}
	public void setListValue(List<String> listValue) {
		this.listValue = listValue;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<FileInfo> getFileInfoes() {
		return fileInfoes;
	}
	public void setFileInfoes(List<FileInfo> fileInfoes) {
		this.fileInfoes = fileInfoes;
	}
	public String getFormSubmitId() {
		return formSubmitId;
	}
	public void setFormSubmitId(String formSubmitId) {
		this.formSubmitId = formSubmitId;
	}
	
}    
