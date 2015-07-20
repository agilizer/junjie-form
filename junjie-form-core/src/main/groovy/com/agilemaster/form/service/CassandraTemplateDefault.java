package com.agilemaster.form.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.form.domain.FileInfo;
import com.agilemaster.form.domain.FormListShow;
import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.domain.FormUser;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.mapping.MappingManager;

public class CassandraTemplateDefault implements CassandraTemplate{
	private static final Logger log = LoggerFactory
			.getLogger(CassandraTemplateDefault.class);
	private Cluster cluster;
	private String contactPoint="127.0.0.1";
	private Session session;
	private InitSchema initSchema;
	private MappingManager mappingManager ; 
	private String keySpace = "junjie_form";
	
	@Override
	public Session getSession() {
		return this.session;
	}

	@Override
	public Cluster getCluster() {
		return this.cluster;
	}
	@Override
	public void init() {
		if(null==cluster){
			log.warn("init warn!!!!!! cluster is null load {} default!",contactPoint);
			cluster = Cluster.builder().addContactPoint(contactPoint).build();
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
	private void initMapper(){
		mappingManager = new MappingManager(session);
		mappingManager.mapper(FormSaas.class);
		mappingManager.mapper(FormUser.class);
		mappingManager.mapper(HtmlForm.class);
		mappingManager.mapper(HtmlInput.class);
		mappingManager.mapper(InputValue.class);
		mappingManager.udtMapper(FormListShow.class);
		mappingManager.udtMapper(FileInfo.class);
	}
	@Override
	public void init(String node) {
		Builder builder = Cluster.builder().addContactPoint(node);
		init(builder);
	}
	@Override
	public void init(Builder builder) {
		cluster = builder.build();
		init();
	}


	@Override
	public void close() {
		if(null!=session){
			session.close();
		}
		if(null!=cluster){
			cluster.close();
		}
	}

	@Override
	public Cluster setCluster(Cluster cluster) {
		return this.cluster=cluster;
	}

	@Override
	public void setInitSchema(InitSchema initSchema) {
		this.initSchema = initSchema;
	}

	@Override
	public MappingManager getMappingManager() {
		return this.mappingManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T save(T object) {
		mappingManager.mapper((Class<T>)object.getClass()).save(object);
		return object;
	}


	@Override
	public <T> T getEntity(Class<T> t, Object id) {
		return mappingManager.mapper(t).get(id);
	}

	public String getContactPoint() {
		return contactPoint;
	}

	public void setContactPoint(String contactPoint) {
		this.contactPoint = contactPoint;
	}

	public InitSchema getInitSchema() {
		return initSchema;
	}

	public void setMappingManager(MappingManager mappingManager) {
		this.mappingManager = mappingManager;
	}

	@Override
	public Select genSelect(String keySpace, String tableName) {
		return QueryBuilder.select().from(keySpace, tableName);
	}

	@Override
	public void setKeySpace(String keySpace) {
		  this.keySpace = keySpace;
	}

	@Override
	public String getKeySpace() {
		return this.keySpace;
	}

	@Override
	public Select genSelect(String tableName) {
		return QueryBuilder.select().from(keySpace, tableName);
	}
}
