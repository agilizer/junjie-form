package com.agilemaster.form.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.agilemaster.cassandra.CassandraJunjieConfig;
import com.agilemaster.cassandra.InitSchema;
import com.agilemaster.cassandra.InitSchemaDefault;
import com.agilemaster.cassandra.option.CassandraTemplate;
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.option.FormSaasOptions;
import com.agilemaster.form.option.FormSaasOptionsImpl;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
public class FormOptionsTest extends BaseTest{

	@Test
	public void testSave(){
		String id = UUID.randomUUID().toString();
		FormSaas formSaas = new FormSaas();
		formSaas.setId(id);
		formOptions.save(formSaas);
		FormSaas search = 	cassandraTemplate.getEntity(FormSaas.class, id);
		assertEquals(id,search.getId());
	}
	
	@Test
	public void testUpdate(){
		String id = UUID.randomUUID().toString();
		FormSaas formSaas = new FormSaas();
		formSaas.setId(id);
		formOptions.save(formSaas);
		Map<String,Object> params = new HashMap<String,Object>();
		Date lastUpdated =  new Date();
		params.put("lastUpdated",lastUpdated);
		formOptions.update(id, params);
		formSaas = formOptions.findOne(id);
		assertEquals(lastUpdated.getTime(),formSaas.getLastUpdated().getTime());
	}

}
