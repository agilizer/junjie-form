package com.agilemaster.form;

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.agilemaster.cassandra.InitSchema
import com.datastax.driver.core.Session

public class InitSchemaForm implements InitSchema{
	private static final Logger log = LoggerFactory
	.getLogger(InitSchemaForm.class);
	private boolean createDrop =  false;
	@Override
	public void init(Session session){
		if(createDrop){
			log.info("<---------------------------Schema createDrop true-------------------->");
			session.execute(InitCql.DROP_CQL);
		}
		session.execute(InitCql.INIT_DEV_KEYSPACE);
		InitCql.INIT_CQL.each{
			session.execute(it);
		}
	}
	@Override
	public void setCreateDrop(boolean createDrop) {
		this.createDrop  = createDrop;
	}
}
