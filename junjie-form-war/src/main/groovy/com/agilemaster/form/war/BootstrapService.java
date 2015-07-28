package com.agilemaster.form.war;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.agilemaster.cassandra.CassandraJunjieConfig;
import com.agilemaster.cassandra.InitSchema;
import com.agilemaster.cassandra.option.CassandraTemplate;
import com.agilemaster.form.InitSchemaForm;
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.option.FormSaasOptions;
import com.agilemaster.form.option.FormSaasOptionsImpl;
import com.agilemaster.form.option.HtmlFormOptions;
import com.agilemaster.form.option.HtmlFormOptionsImpl;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;

@Service
public class BootstrapService {
	private  final Logger log = LoggerFactory
			.getLogger(this.getClass());
	private CassandraTemplate cassandraTemplate ;
	FormSaasOptions formOptions ;
	private static String saasKey="946c4eea-15cb-4dfb-8f8d-91b99fe78939";
	private static String accessKey="c8d47cffb16e4668bc84b3b4f9f72023";
	
	@Bean(name="cassandraTemplate")
	public CassandraTemplate initCassandraTemplate(){
		/**
		 * init cassandra 
		 */
		Builder builder = Cluster.builder().addContactPoint("127.0.0.1");
		/**
		 * 设置为删除创建
		 */
		InitSchema initSchema = new InitSchemaForm();
		initSchema.setCreateDrop(true);
		CassandraJunjieConfig.setInitSchema(initSchema);
		CassandraJunjieConfig.setKeySpace(JunjieFormConstants.DEFAULT_KEY_SPACE);
		CassandraJunjieConfig.setMappingPackage("com.agilemaster.form.domain");
		CassandraJunjieConfig.init(builder);
		cassandraTemplate = CassandraJunjieConfig.getInstance();
		formOptions =  new FormSaasOptionsImpl();
		FormSaas formSaas = formOptions.findOne(saasKey);
		if(formSaas==null){
			formSaas = new FormSaas();
			formSaas.setId(saasKey);
			formSaas.setAccessKey(accessKey);
			formOptions.save(formSaas);
		}
		return cassandraTemplate;
	}
	@Bean 
	@DependsOn("cassandraTemplate")
	public FormSaasOptions formSaasOptions(){
		return new FormSaasOptionsImpl(); 
	}
	@DependsOn("cassandraTemplate")
	@Bean HtmlFormOptions htmlFormOptions(){
		return new HtmlFormOptionsImpl();
	}
	
	@PreDestroy
	public void destory(){
		CassandraJunjieConfig.close();
	}
}
