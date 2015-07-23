package com.agilemaster.form.option.counter;

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.agilemaster.form.constants.JunjieFormConstants
import com.agilemaster.form.domain.FormSaasCounter

public class FormSaasCounterOptions extends BaseCounterOptions implements CounterOptionsInterface{
	private static final Logger log = LoggerFactory
			.getLogger(FormSaasCounterOptions.class);
	@Override
	public void inc(String id) {
		inc(id,1);
	}
	@Override
	public void dec(String id) {
		dec(id,1);
	}

	@Override
	public void inc(String id, long count) {
		this.inc(JunjieFormConstants.T_FORM_SAAS_COUNTER, id, count);
	}

	@Override
	public void dec(String id, long count) {
		dec(JunjieFormConstants.T_FORM_SAAS_COUNTER, id, count);
	}
	@Override
	public long getCount(String id) {
		FormSaasCounter counter = cassandraTemplate.getEntity(FormSaasCounter.class, id);
		long result = 0;
		if(counter){
			result = counter.getCounterValue();
		}
		return result;
	}
}
