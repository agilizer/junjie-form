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
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class CassandraTemplateDefault implements CassandraTemplate {
	private static final Logger log = LoggerFactory
			.getLogger(CassandraTemplateDefault.class);
	private Session session;
	private MappingManager mappingManager;

	public CassandraTemplateDefault() {
		
	}

	public CassandraTemplateDefault(Session session,
			MappingManager mappingManager) {
		this.session = session;
		this.mappingManager = mappingManager;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setMappingManager(MappingManager mappingManager) {
		this.mappingManager = mappingManager;
	}

	@Override
	public Session getSession() {
		return this.session;
	}

	@Override
	public MappingManager getMappingManager() {
		return this.mappingManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T save(T object) {
		mappingManager.mapper((Class<T>) object.getClass()).save(object);
		return object;
	}

	@Override
	public <T> T getEntity(Class<T> t, Object id) {
		return mappingManager.mapper(t).get(id);
	}

	@Override
	public <T> void delete(T object) {
		mappingManager.mapper((Class<T>) object.getClass()).delete(object);
	}

	@Override
	public <T> void deleteById(Class<T> t, Object id) {
		mappingManager.mapper(t).delete(id);
		;
	}

	@Override
	public <T> Mapper<T> getMapper(Class<T> t) {
		return mappingManager.mapper(t);
	}
}
