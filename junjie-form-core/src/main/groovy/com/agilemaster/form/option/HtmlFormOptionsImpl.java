package com.agilemaster.form.option;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.form.CassandraJunjieForm;
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.domain.FormListShow;
import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.domain.FormUser;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;

public class HtmlFormOptionsImpl implements HtmlFormOptions{
	private static final Logger log = LoggerFactory
			.getLogger(HtmlFormOptionsImpl.class);
	private CassandraTemplate cassandraTemplate = CassandraJunjieForm.getInstance();
	@Override
	public HtmlForm save(HtmlForm htmlForm) {
		if(null!=htmlForm){
			if(null==htmlForm.getId()){
				String id = UUID.randomUUID().toString();
				htmlForm.setId(id);
			}
			Date date = new Date();
			htmlForm.setDateCreated(date);
			htmlForm.setLastUpdated(date);
			cassandraTemplate.save(htmlForm);
		}else{
			throw new NullPointerException("save FormSaas is null!");
		}
		return htmlForm;
	}
	@Override
	public HtmlForm delete(HtmlForm domain) {
		cassandraTemplate.delete(domain);
		return domain;
	}
	@Override
	public void delete(String id) {
		cassandraTemplate.deleteById(HtmlForm.class, id);
	}
	@Override
	public boolean update(String id, Map<String, Object> params) {
		List<Clause> whereList = new ArrayList<Clause>();
		whereList.add(eq("id",id));
		cassandraTemplate.update(JunjieFormConstants.T_HTML_FORM, params, whereList);
		return true;
	}
	@Override
	public HtmlForm findOne(Object id) {
		return cassandraTemplate.getEntity(HtmlForm.class, id);
	}
	@Override
	public long count() {
		return 0;
	}
	
}
