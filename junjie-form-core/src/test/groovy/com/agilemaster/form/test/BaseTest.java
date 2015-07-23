package com.agilemaster.form.test;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.form.CassandraJunjieForm;
import com.agilemaster.form.InitSchema;
import com.agilemaster.form.InitSchemaDefault;
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.option.CassandraTemplate;
import com.agilemaster.form.option.FormSaasOptionsImpl;
import com.agilemaster.form.option.FormSaasOptions;
import com.agilemaster.form.option.HtmlFormOptionsImpl;
import com.agilemaster.form.option.HtmlFormOptions;
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
		InitSchema initSchema = new InitSchemaDefault();
		initSchema.setCreateDrop(true);
		CassandraJunjieForm.setInitSchema(initSchema);
		CassandraJunjieForm.init(builder);
		CassandraJunjieForm.setKEY_SPACE(JunjieFormConstants.DEFAULT_KEY_SPACE);
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
