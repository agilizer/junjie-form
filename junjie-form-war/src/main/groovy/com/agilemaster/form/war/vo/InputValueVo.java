package com.agilemaster.form.war.vo;


public class InputValueVo {
	private String label;
	private String answerId;
	private String formId;
	private  String htmlInputId;
	private String strValue;
	private boolean answerRight = false;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
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
	public boolean isAnswerRight() {
		return answerRight;
	}
	public void setAnswerRight(boolean answerRight) {
		this.answerRight = answerRight;
	}
	
	
}
