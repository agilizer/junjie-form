package com.agilemaster.form.service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.form.CassandraJunjieForm;
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
import com.datastax.driver.mapping.MappingManager;

public class HtmlFormOptionsImpl implements HtmlFormOptions{
	private static final Logger log = LoggerFactory
			.getLogger(HtmlFormOptionsImpl.class);
	private CassandraTemplate cassandraTemplate = CassandraJunjieForm.getInstance();
	@Override
	public HtmlForm save(HtmlForm domain) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HtmlForm delete(HtmlForm domain) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public HtmlForm update(String id, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HtmlForm findOne(Object id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
