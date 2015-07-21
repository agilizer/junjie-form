package com.agilemaster.form.service;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Service;

import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

@Service
public class FormOptionsImpl implements FormOptions{
	private static final Logger log = LoggerFactory
			.getLogger(FormOptionsImpl.class);
	@Qualifier("junjieFormcassandraOperations")
	@Autowired()
	private CassandraOperations cassandraOperations;

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
			cassandraOperations.insert(formSaas);
		}
		return formSaas;
	}

	@Override
	public HtmlForm createForm(String saasId, HtmlForm htmlForm) {
		if(null!=htmlForm){
			//TODO check FormSaas exist
			htmlForm.setSaasId(saasId);
			htmlForm.setId( UUID.randomUUID().toString());
			cassandraOperations.insert(htmlForm);
		}
		return htmlForm;
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
	public FormSaas findFormSaasOne(String id) {
		Select select = QueryBuilder.select().from(JunjieFormConstants.T_FORM_SAAS);
		select.where(QueryBuilder.eq("id", id));
		FormSaas formSaas = cassandraOperations.selectOne(select, FormSaas.class);
		return formSaas;
	}
}
