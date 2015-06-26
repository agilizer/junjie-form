package com.agilemaster.form.service;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.mapping.MappingManager;

public interface CassandraTemplate {
	public Session getSession();
	public Cluster getCluster();
	public Cluster setCluster(Cluster cluster);
	MappingManager getMappingManager();
	void setInitSchema(InitSchema initSchma);
	/**
	 * note ,must call {@link #setCluser} first.
	 * if cluster  is null,load localhost default;
	 */
	void init();
	public void init(Builder builder);
	public void init(String node);
	void close();
	<T> T save(T object);
	<T> T getEntity(Class<T> t,Object id);
}
