package com.agilemaster.form.option.counter;

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.agilemaster.form.CassandraJunjieForm
import com.agilemaster.form.constants.JunjieFormConstants
import com.agilemaster.form.domain.HtmlFormCounter
import com.agilemaster.form.option.CassandraTemplate

public class HtmlFormCounterOptions implements CounterOptionsInterface{
	private static final Logger log = LoggerFactory
	.getLogger(HtmlFormCounterOptions.class);
	private CassandraTemplate cassandraTemplate = CassandraJunjieForm.getInstance();
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
		this.inc(JunjieFormConstants.T_HTML_FORM_COUNTER, id, count);
	}

	@Override
	public void dec(String id, long count) {
		dec(JunjieFormConstants.T_HTML_FORM_COUNTER, id, count);
	}

	@Override
	public long getCount(String id) {
		HtmlFormCounter counter = cassandraTemplate.getEntity(HtmlFormCounter.class, id);
		long result = 0;
		if(counter){
			result = counter.getCounterValue();
		}
		return result;
	}
}
