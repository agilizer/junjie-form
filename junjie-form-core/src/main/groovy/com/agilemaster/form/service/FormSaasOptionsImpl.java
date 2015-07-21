package com.agilemaster.form.service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.agilemaster.form.CassandraJunjieForm;
import com.agilemaster.form.domain.FormSaas;

public class FormSaasOptionsImpl implements FormSaasOptions{

	private CassandraTemplate cassandraTemplate = CassandraJunjieForm.getInstance();
	@Override
	public FormSaas save(FormSaas formSaas) {
		if(null!=formSaas){
			if(null==formSaas.getId()){
				String id = UUID.randomUUID().toString();
				formSaas.setId(id);
			}
			Date date = new Date();
			formSaas.setDateCreated(date);
			formSaas.setLastUpdated(date);
			cassandraTemplate.save(formSaas);
		}else{
			throw new NullPointerException("save FormSaas is null!");
		}
		return formSaas;
	}

	@Override
	public FormSaas delete(FormSaas domain) {
		cassandraTemplate.delete(domain);
		return domain;
	}

	@Override
	public void delete(String id) {
		cassandraTemplate.deleteById(FormSaas.class, id);
	}

	@Override
	public FormSaas update(String id, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FormSaas findOne(Object id) {
		return cassandraTemplate.getEntity(FormSaas.class, id);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
