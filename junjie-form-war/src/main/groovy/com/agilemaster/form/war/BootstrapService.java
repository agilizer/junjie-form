package com.agilemaster.form.war;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.agilemaster.cassandra.CassandraJunjieConfig;
import com.agilemaster.cassandra.InitSchema;
import com.agilemaster.cassandra.option.CassandraTemplate;
import com.agilemaster.form.InitSchemaForm;
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.option.AnswerCacheOptions;
import com.agilemaster.form.option.AnswerCacheOptionsImpl;
import com.agilemaster.form.option.FileInfoOptions;
import com.agilemaster.form.option.FileInfoOptionsImpl;
import com.agilemaster.form.option.FormSaasOptions;
import com.agilemaster.form.option.FormSaasOptionsImpl;
import com.agilemaster.form.option.HtmlFormOptions;
import com.agilemaster.form.option.HtmlFormOptionsImpl;
import com.agilemaster.form.option.HtmlInputOptions;
import com.agilemaster.form.option.HtmlInputOptionsImpl;
import com.agilemaster.form.option.InputValueOptions;
import com.agilemaster.form.option.InputValueOptionsImpl;
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
	@Value("#${app.name}")
	private String appName;
	
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
		initSchema.setCreateDrop(false);
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
		log.info("appName--------------------------------->"+appName);
		return cassandraTemplate;
	}
	@Bean 
	@DependsOn("cassandraTemplate")
	public FormSaasOptions formSaasOptions(){
		return new FormSaasOptionsImpl(); 
	}
	@Bean 
	@DependsOn("cassandraTemplate")
	public AnswerCacheOptions answerCacheOptions(){
		return new AnswerCacheOptionsImpl(); 
	}
	
	@DependsOn("cassandraTemplate")
	@Bean HtmlFormOptions htmlFormOptions(){
		return new HtmlFormOptionsImpl();
	}
	@DependsOn("cassandraTemplate")
	@Bean InputValueOptions inputValueOptions(){
		return new InputValueOptionsImpl();
	}
	@DependsOn("cassandraTemplate")
	@Bean HtmlInputOptions htmlInputOptions(){
		return new HtmlInputOptionsImpl();
	}
	@DependsOn("cassandraTemplate")
	@Bean FileInfoOptions fileInfoOptions(){
		return new FileInfoOptionsImpl();
	}
	
	@PreDestroy
	public void destory(){
		CassandraJunjieConfig.close();
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
}
