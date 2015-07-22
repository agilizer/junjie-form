package com.agilemaster.form.option;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.form.CassandraJunjieForm;
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.domain.FormSaas;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.set;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.querybuilder.Clause;

public class FormSaasOptionsImpl implements FormSaasOptionsInter{
	private static final Logger log = LoggerFactory
			.getLogger(FormSaasOptionsImpl.class);
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
	public boolean update(String id, Map<String, Object> params) {
		List<Clause> whereList = new ArrayList<Clause>();
		whereList.add(eq("id",id));
		cassandraTemplate.update(JunjieFormConstants.T_FORM_SAAS, params, whereList);
		return true;
	}

	@Override
	public FormSaas findOne(Object id) {
		return cassandraTemplate.getEntity(FormSaas.class, id);
	}

	@Override
	public long count() {
		ResultSet resultSet = cassandraTemplate.execute("select count(*) from "+JunjieFormConstants.DEFAULT_KEY_SPACE+"."+JunjieFormConstants.T_FORM_SAAS);
		return resultSet.one().getLong(0);
	}
	
}
