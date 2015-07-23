package com.agilemaster.form.test;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.cassandra.CassandraJunjieForm;
import com.agilemaster.cassandra.InitSchema;
import com.agilemaster.cassandra.option.CassandraTemplate;
import com.agilemaster.form.InitSchemaForm;
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.option.FormSaasOptions;
import com.agilemaster.form.option.FormSaasOptionsImpl;
import com.agilemaster.form.option.HtmlFormOptions;
import com.agilemaster.form.option.HtmlFormOptionsImpl;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;

public class BaseTest {
	private  final Logger log = LoggerFactory
			.getLogger(this.getClass());
	FormSaasOptions formOptions ;
	HtmlFormOptions htmlFormOptions;
	CassandraTemplate cassandraTemplate ;
	@Before
	public void before(){
		Builder builder = Cluster.builder().addContactPoint("127.0.0.1");
		/**
		 * 设置为删除创建
		 */
		InitSchema initSchema = new InitSchemaForm();
		initSchema.setCreateDrop(true);
		CassandraJunjieForm.setInitSchema(initSchema);
		CassandraJunjieForm.setKeySpace(JunjieFormConstants.DEFAULT_KEY_SPACE);
		CassandraJunjieForm.setMappingPackage("com.agilemaster.form.domain");
		CassandraJunjieForm.init(builder);
		
		cassandraTemplate = CassandraJunjieForm.getInstance();
		formOptions =  new FormSaasOptionsImpl();
		htmlFormOptions = new HtmlFormOptionsImpl();
	}
	
	@After
	public void after(){
		CassandraJunjieForm.close();
	}
	
	public FormSaas createFormSaas(){
		String id = UUID.randomUUID().toString();
		FormSaas formSaas = new FormSaas();
		formSaas.setId(id);
		formOptions.save(formSaas);
		return formSaas;
	}
	public HtmlForm createHtmlForm(){
		FormSaas formSaas = createFormSaas();
		HtmlForm htmlForm = new HtmlForm();
		htmlForm.setSaasId(formSaas.getId());
		htmlForm = htmlFormOptions.save(htmlForm);
		return htmlForm;
	}
}
