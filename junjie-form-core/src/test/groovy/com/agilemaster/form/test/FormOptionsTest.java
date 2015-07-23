package com.agilemaster.form.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.agilemaster.form.CassandraJunjieForm;
import com.agilemaster.form.InitSchema;
import com.agilemaster.form.InitSchemaDefault;
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.option.CassandraTemplate;
import com.agilemaster.form.option.FormSaasOptions;
import com.agilemaster.form.option.FormSaasOptionsImpl;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
public class FormOptionsTest{
	FormSaasOptions formOptions ;
	CassandraTemplate cassandraTemplate ;
	@Before
	public void before(){
		Builder builder = Cluster.builder().addContactPoint("127.0.0.1");
		/**
		 * 设置为删除创建
		 */
		InitSchema initSchema = new InitSchemaDefault();
		initSchema.setCreateDrop(true);
		CassandraJunjieForm.setInitSchema(initSchema);
		
		
		CassandraJunjieForm.init(builder);
		CassandraJunjieForm.setKEY_SPACE(JunjieFormConstants.DEFAULT_KEY_SPACE);
		cassandraTemplate = CassandraJunjieForm.getInstance();
		formOptions =  new FormSaasOptionsImpl();
	}
	
	@After
	public void after(){
		CassandraJunjieForm.close();
	}
	
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
