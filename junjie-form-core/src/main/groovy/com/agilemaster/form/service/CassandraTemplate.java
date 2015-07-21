package com.agilemaster.form.service;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public interface CassandraTemplate {
	public Session getSession();
	MappingManager getMappingManager();

	<T> Mapper<T> getMapper(Class<T> t);

	<T> T save(T object);

	<T> T getEntity(Class<T> t, Object id);

	<T> void delete(T object);

	<T> void deleteById(Class<T> t, Object id);
}
