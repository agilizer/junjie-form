package com.agilemaster.form.option.counter;

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.agilemaster.form.CassandraJunjieForm
import com.agilemaster.form.constants.JunjieFormConstants
import com.agilemaster.form.option.CassandraTemplate

public class BaseCounterOptions {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	protected CassandraTemplate cassandraTemplate = CassandraJunjieForm
	.getInstance();

	public void inc(String tableName, String id, long value) {
		def cql = """update  ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${tableName}  
				set counterValue=counterValue+${value} where id='${id}';"""
		cassandraTemplate.execute(cql);
	}

	public void dec(String tableName, String id, long value) {
		def cql = """update  ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${tableName}
				set counterValue=counterValue-${value} where id='${id}';"""
		cassandraTemplate.execute(cql);
	}
}
