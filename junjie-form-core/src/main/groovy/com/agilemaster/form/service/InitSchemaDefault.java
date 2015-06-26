package com.agilemaster.form.service;

import com.datastax.driver.core.Session;

public class InitSchemaDefault implements InitSchema{

	@Override
	public void init(Session session){
		session.execute(InitCql.INIT_DEV_KEYSPACE);
		session.execute(InitCql.INIT_CQL);
	}

}
