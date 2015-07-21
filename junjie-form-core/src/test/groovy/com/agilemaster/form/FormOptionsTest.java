package com.agilemaster.form;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.service.CassandraTemplate;
import com.agilemaster.form.service.FormSaasOptions;
import com.agilemaster.form.service.FormSaasOptionsImpl;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
public class FormOptionsTest{
	FormSaasOptions formOptions ;
	CassandraTemplate cassandraTemplate ;
	@Before
	public void before(){
		Builder builder = Cluster.builder().addContactPoint("127.0.0.1");
		CassandraJunjieForm.init(builder);
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

}
