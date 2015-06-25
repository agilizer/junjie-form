package com.agilemaster.form.domain;

import java.io.Serializable;
import java.util.List;

public class HtmlForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 440632749764772923L;
	
	
	private String id;
	/**
	 * 表单的名称(必填)
	 */
	private String name;
	/**
	 * 表单前显示的html代码(可选)
	 */
	private String beforeText;
	/**
	 * 表单后显示的html代码 (可选)
	 */
	private String afterText;
	/**
	 * 表单提交的action(必填)
	 */
	private String action;
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
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<HtmlInput> getHtmlInputs() {
		return htmlInputs;
	}
	public void setHtmlInputs(List<HtmlInput> htmlInputs) {
		this.htmlInputs = htmlInputs;
	}
}
