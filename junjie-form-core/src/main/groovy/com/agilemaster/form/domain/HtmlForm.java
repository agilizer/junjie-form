package com.agilemaster.form.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class HtmlForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 440632749764772923L;	
	
	private String id;
	private String saasId;
	/**
	 * 表单的名称(必填)
	 */
	private String name;
	
	private String description;
	
	private Map<String,String> customInfo;
	private List<HtmlInput> htmlInputs;
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
	public List<HtmlInput> getHtmlInputs() {
		return htmlInputs;
	}
	public void setHtmlInputs(List<HtmlInput> htmlInputs) {
		this.htmlInputs = htmlInputs;
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
	
}
