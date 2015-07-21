package com.agilemaster.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.form.domain.FileInfo;
import com.agilemaster.form.domain.FormListShow;
import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.domain.FormUser;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.agilemaster.form.service.CassandraTemplate;
import com.agilemaster.form.service.CassandraTemplateDefault;
import com.agilemaster.form.service.InitSchema;
import com.agilemaster.form.service.InitSchemaDefault;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.mapping.MappingManager;

public class CassandraJunjieForm {
	private static Cluster cluster;
	private static Session session;
	private static InitSchema initSchema;
	private static MappingManager mappingManager;
	private static CassandraTemplate cassandraTemplate ;
	private static final Logger log = LoggerFactory
			.getLogger(CassandraJunjieForm.class);
	public static CassandraTemplate getInstance(){
		if(cassandraTemplate==null){
			cassandraTemplate = new CassandraTemplateDefault(session,mappingManager);
		}
		return cassandraTemplate;
	}
	
	public static  void init() {
		if(null==cluster){
			log.warn("init warn!!!!!! cluster is null load 127.0.0.1 default!");
			cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
		}
		Metadata metadata = cluster.getMetadata();
		log.info("Connected to cluster: {}\n",
				metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			log.info("Datatacenter: {}; Host: {}; Rack: {}\n",
					host.getDatacenter(), host.getAddress(), host.getRack());
		}
		session = cluster.connect();
		if(null==initSchema){
			initSchema = new InitSchemaDefault();
		}
		initSchema.init(session);
		initMapper();
	}
	public static void init(String node) {
		Builder builder = Cluster.builder().addContactPoint(node);
		init(builder);
	}
	public static void init(Builder builder) {
		cluster = builder.build();
		init();
	}	
	
	public static Session getSession() {
		return session;
	}

	public static Cluster getCluster() {
		return cluster;
	}
	
	private static void initMapper(){
		mappingManager = new MappingManager(session);
		mappingManager.mapper(FormSaas.class);
		mappingManager.mapper(FormUser.class);
		mappingManager.mapper(HtmlForm.class);
		mappingManager.mapper(HtmlInput.class);
		mappingManager.mapper(InputValue.class);
		mappingManager.udtMapper(FormListShow.class);
		mappingManager.udtMapper(FileInfo.class);
	}

	public static void close() {
		if(null!=session){
			session.close();
		}
		if(null!=cluster){
			cluster.close();
		}
	}

	public static Cluster setCluster(Cluster clusterInput) {
		return cluster=clusterInput;
	}

	public static void setInitSchema(InitSchema initSchemaInput) {
		initSchema = initSchemaInput;
	}

	public static MappingManager getMappingManager() {
		return mappingManager;
	}

}
