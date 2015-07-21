package com.agilemaster.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.convert.CassandraConverter;
import org.springframework.data.cassandra.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;

import com.agilemaster.form.service.InitSchema;
import com.agilemaster.form.service.InitSchemaDefault;

@Configuration
@PropertySource(value = { "classpath:cassandra.properties" })
@ComponentScan(basePackages = {"com.agilemaster.form.service"})
public class CassandraConfig {
	private static final Logger LOG = LoggerFactory
			.getLogger(CassandraConfig.class);
	@Autowired
	private Environment env;

	@Bean
	public CassandraClusterFactoryBean cluster() {
		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints(env.getProperty("cassandra.contactpoints"));
		cluster.setPort(Integer.parseInt(env.getProperty("cassandra.port")));
		return cluster;
	}

	@Bean
	public CassandraMappingContext mappingContext() throws Exception {
	    BasicCassandraMappingContext bean = new BasicCassandraMappingContext(); 
	    bean.setInitialEntitySet(CassandraEntityClassScanner.scan(("com.agilemaster.form.domain")));
	    return bean;
	}

	@Bean
	public CassandraConverter converter() throws Exception {
		return new MappingCassandraConverter(mappingContext());
	}

	@Bean
	public CassandraSessionFactoryBean session() throws Exception {
		CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
		session.setCluster(cluster().getObject());
		session.setKeyspaceName(env.getProperty("cassandra.keyspace"));
		session.setConverter(converter());
		session.setSchemaAction(SchemaAction.RECREATE_DROP_UNUSED);
		return session;
	}

	@Bean(name="junjieFormcassandraOperations")
	public CassandraOperations cassandraTemplate() throws Exception {
		CassandraOperations operations = new CassandraTemplate(session().getObject());
//		InitSchema initSchema = new InitSchemaDefault();
//		initSchema.setCreateDrop(Boolean.parseBoolean(env.getProperty("create.drop")));
//		initSchema.init(operations.getSession());
		return operations;
	}
	
}
