package com.agilemaster.form.service;

import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;


public interface FormOptions {
	/**
	 * 设置cassandra访问模板 
	 * @param cassandraTemplate
	 */
	void setCassandraTemplate(CassandraTemplate cassandraTemplate);
	/**
	 * 创建一个帐号，id随机生成，返回时id有值。
	 * @return
	 */
	FormSaas createFormSaas();
	/**
	 * 指定id创建formSaas帐号
	 * @param saasId
	 * @return
	 */
	FormSaas createFormSass(String saasId);
	/**
	 * 根据saasId，和htmlForm，创建一个htmlForm表单实例。
	 * @param saasId
	 * @param htmlForm
	 * @return
	 */
	HtmlForm createForm(String saasId,HtmlForm htmlForm);
	/**
	 * 根据name和description创建表单。
	 * @param saasId
	 * @param name
	 * @param description
	 * @return
	 */
	HtmlForm createForm(String saasId,String name,String description);
	/**
	 * 根据name创建表单
	 * @param saasId
	 * @param name
	 * @return
	 */
	HtmlForm createForm(String saasId,String name);
	boolean updateForm(String fromId,String name,String description);
	boolean updateForm(String fromId,String name);
	boolean updateFormDesc(String fromId,String description);
	HtmlInput createInput(HtmlInput htmlInput);
	HtmlInput updateInput(HtmlInput htmlInput);
	
	InputValue createValue(InputValue inputValue);
	
	InputValue updateValue(InputValue inputValue);
}
