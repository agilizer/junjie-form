package com.agilemaster.form.war.vo;

import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;


public class FormVo {

	private String label;
	@JSONField(name="field_type")
	private FiledType filedType;
	private boolean required;
	@JSONField(name="field_options")
	private Map<String,Object> fieldOptions;
	private String cid;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public FiledType getFiledType() {
		return filedType;
	}
	public void setFiledType(FiledType filedType) {
		this.filedType = filedType;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public Map<String, Object> getFieldOptions() {
		return fieldOptions;
	}
	public void setFieldOptions(Map<String, Object> fieldOptions) {
		this.fieldOptions = fieldOptions;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	
	
	
}
