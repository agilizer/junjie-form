package com.agilemaster.form.service;

import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;


public interface FormOptions {
	void setCassandraTemplate(CassandraTemplate cassandraTemplate);
	FormSaas createFormSaas();
	FormSaas createFormSass(String saasId);
	HtmlForm createForm(String saasId,HtmlForm htmlForm);
	HtmlForm createForm(String saasId,String name,String description);
	HtmlForm createForm(String saasId,String name);
	boolean updateForm(String fromId,String name,String description);
	boolean updateForm(String fromId,String name);
	boolean updateFormDesc(String fromId,String description);
	HtmlInput createInput(HtmlInput htmlInput);
	HtmlInput updateInput(HtmlInput htmlInput);
	
	InputValue createValue(InputValue inputValue);
	
	InputValue updateValue(InputValue inputValue);
}
