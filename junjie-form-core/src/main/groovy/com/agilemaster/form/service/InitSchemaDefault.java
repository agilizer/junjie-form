package com.agilemaster.form.service;

import com.datastax.driver.core.Session;

public class InitSchemaDefault implements InitSchema{

	private boolean createDrop = true;
	@Override
	public void init(Session session){
		if(createDrop){
			session.execute(InitCql.DROP_CQL);
		}
		session.execute(InitCql.INIT_DEV_KEYSPACE);
		for(String str:InitCql.INIT_CQL){
			session.execute(str);
		}
	}
}
