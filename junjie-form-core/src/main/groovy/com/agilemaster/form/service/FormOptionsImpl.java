package com.agilemaster.form.service;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.form.domain.FormListShow;
import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.domain.FormUser;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

public class FormOptionsImpl implements FormOptions{
	private static final Logger log = LoggerFactory
			.getLogger(FormOptionsImpl.class);
	private CassandraTemplate cassandraTemplate;

	@Override
	public FormSaas createFormSaas() {
		String id = UUID.randomUUID().toString();
		return createFormSass(id) ;
	}

	@Override
	public FormSaas createFormSass(String saasId) {
		FormSaas formSaas  = null;
		if(null==saasId){
			throw new NullPointerException(" saasId is null!");
		}else{
			formSaas = new FormSaas();
			Date date = new Date();
			formSaas.setDateCreated(date);
			formSaas.setId(saasId);
			formSaas.setLastUpdated(date);
			cassandraTemplate.save(formSaas);
		}
		return formSaas;
	}

	@Override
	public HtmlForm createForm(String sassId, HtmlForm htmlForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HtmlForm createForm(String sassId, String name, String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HtmlForm createForm(String sassId, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateForm(String fromId, String name, String description) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateForm(String fromId, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateFormDesc(String fromId, String description) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HtmlInput createInput(HtmlInput htmlInput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HtmlInput updateInput(HtmlInput htmlInput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputValue createValue(InputValue inputValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputValue updateValue(InputValue inputValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCassandraTemplate(CassandraTemplate cassandraTemplate) {
		this.cassandraTemplate = cassandraTemplate;
	}

	

	
}
