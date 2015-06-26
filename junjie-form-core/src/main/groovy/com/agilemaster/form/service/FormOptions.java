package com.agilemaster.form.service;

import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Session;


public interface FormOptions {
	public Session getSession();
	public Cluster getCluster();
	public Cluster setCluster(Cluster cluster);
	void setInitSchema(InitSchema initSchma);
	/**
	 * note ,must call {@link #setCluser} first.
	 * if cluster  is null,load localhost default;
	 */
	void init();
	public void init(Builder builder);
	public void init(String node);
	void close();
	
	FormSaas createFormSass();
	FormSaas createFormSass(String saasId);
	HtmlForm createForm(String sassId,HtmlForm htmlForm);
	HtmlForm createForm(String sassId,String name,String description);
	HtmlForm createForm(String sassId,String name);
	boolean updateForm(String fromId,String name,String description);
	boolean updateForm(String fromId,String name);
	boolean updateFormDesc(String fromId,String description);
	HtmlInput createInput(HtmlInput htmlInput);
	HtmlInput updateInput(HtmlInput htmlInput);
	
	InputValue createValue(InputValue inputValue);
	
	InputValue updateValue(InputValue inputValue);
}
