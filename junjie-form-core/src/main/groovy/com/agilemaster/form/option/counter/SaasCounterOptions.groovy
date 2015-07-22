package com.agilemaster.form.option.counter;

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.agilemaster.form.CassandraJunjieForm
import com.agilemaster.form.constants.JunjieFormConstants
import com.agilemaster.form.domain.SaasCounter
import com.agilemaster.form.option.CassandraTemplate

public class SaasCounterOptions {
	private static final Logger log = LoggerFactory
			.getLogger(SaasCounterOptions.class);
	private CassandraTemplate cassandraTemplate = CassandraJunjieForm.getInstance();
	public void inc(long count) {
		def cql = """update  ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_SAAS_COUNTER}  
					set saasCounter=saasCounter+${count} where id='${JunjieFormConstants.SAAS_COUNT_ID}';"""
		cassandraTemplate.execute(cql);
	}

	public void dec(long count) {
		def cql = """update  ${JunjieFormConstants.DEFAULT_KEY_SPACE}.${JunjieFormConstants.T_SAAS_COUNTER}
					set saasCounter=saasCounter-${count} where id='${JunjieFormConstants.SAAS_COUNT_ID}';"""
		cassandraTemplate.execute(cql);
	}
	
	public long getCount(){
		SaasCounter counter = cassandraTemplate.getEntity(SaasCounter.class, JunjieFormConstants.SAAS_COUNT_ID);
		long result = 0;
		if(counter){
			result = counter.getCounterValue();
		}
		return result;
	}

	public void inc() {
		inc(1);
	}

	public void dec() {
		dec(1);
	}

}
