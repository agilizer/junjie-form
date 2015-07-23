package com.agilemaster.form.war;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import com.agilemaster.form.CassandraJunjieForm;
import com.agilemaster.form.InitSchema;
import com.agilemaster.form.InitSchemaDefault;
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.option.CassandraTemplate;
import com.agilemaster.form.option.FormSaasOptionsImpl;
import com.agilemaster.form.option.FormSaasOptions;
import com.agilemaster.form.option.HtmlFormOptionsImpl;
import com.agilemaster.form.option.HtmlFormOptions;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;

@Service
public class BootstrapService {
	private  final Logger log = LoggerFactory
			.getLogger(this.getClass());
	private CassandraTemplate cassandraTemplate ;
	@PostConstruct
	public  void init(){
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
	}
	@Bean
	public CassandraTemplate initCassandraTemplate(){
		return cassandraTemplate;
	}
	@Bean 
	public FormSaasOptions formSaasOptions(){
		return new FormSaasOptionsImpl(); 
	}
	@Bean HtmlFormOptions htmlFormOptions(){
		return new HtmlFormOptionsImpl();
	}
	
	@PreDestroy
	public void destory(){
		CassandraJunjieForm.close();
	}
}
