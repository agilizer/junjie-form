package com.agilemaster.form;

import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.form.domain.FileInfo;
import com.agilemaster.form.domain.FormListShow;
import com.agilemaster.form.domain.FormUser;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.domain.HtmlFormCounter;
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.agilemaster.form.option.CassandraTemplate;
import com.agilemaster.form.option.CassandraTemplateDefault;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.UDT;

public class CassandraJunjieForm {
	private static Cluster cluster;
	private static Session session;
	private static InitSchema initSchema;
	private static MappingManager mappingManager;
	private static CassandraTemplate cassandraTemplate ;
	private static String mappingPackage="com.agilemaster.form.domain";
	private static String KEY_SPACE="junjie_form";
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
	
	@SuppressWarnings("unchecked")
	private static void initMapper(){
		mappingManager = new MappingManager(session);
		Reflections reflections = new Reflections(mappingPackage);
		 Set<Class<?>> tableDomains = reflections.getTypesAnnotatedWith(Table.class);
		 for(Class t:tableDomains){
			 mappingManager.mapper(t);
		 }
		 
		 Set<Class<?>> udtDomains = reflections.getTypesAnnotatedWith(UDT.class);
		 for(Class t:udtDomains){
			 mappingManager.udtMapper(t);
		 }
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

	public static String getKEY_SPACE() {
		return KEY_SPACE;
	}

	public static void setKEY_SPACE(String kEY_SPACE) {
		KEY_SPACE = kEY_SPACE;
	}

	public static String getMappingPackage() {
		return mappingPackage;
	}

	public static void setMappingPackage(String mappingPackageInput) {
		mappingPackage = mappingPackageInput;
	}
	
	

}
