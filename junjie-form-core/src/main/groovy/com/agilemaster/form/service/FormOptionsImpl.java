package com.agilemaster.form.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class FormOptionsImpl implements FormOptions{
	private static final Logger log = LoggerFactory
			.getLogger(FormOptionsImpl.class);
	private Cluster cluster;
	private Session session;
	private InitSchema initSchema;
	
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
	public FormSaas createFormSass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FormSaas createFormSass(String saasId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HtmlForm createForm(String sassId, HtmlForm htmlForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HtmlForm createForm(String sassId, String name, String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HtmlForm createForm(String sassId, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateForm(String fromId, String name, String description) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateForm(String fromId, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateFormDesc(String fromId, String description) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HtmlInput createInput(HtmlInput htmlInput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HtmlInput updateInput(HtmlInput htmlInput) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputValue createValue(InputValue inputValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputValue updateValue(InputValue inputValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cluster setCluster(Cluster cluster) {
		// TODO Auto-generated method stub
		return this.cluster=cluster;
	}

	@Override
	public void setInitSchema(InitSchema initSchma) {
		this.initSchema = initSchema;
	}
}
