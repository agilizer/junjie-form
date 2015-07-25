package com.agilemaster.form.option;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.cassandra.CassandraJunjieConfig;
import com.agilemaster.cassandra.option.CassandraTemplate;
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.domain.HtmlForm;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.querybuilder.Clause;

public class HtmlFormOptionsImpl implements HtmlFormOptions{
	private static final Logger log = LoggerFactory
			.getLogger(HtmlFormOptionsImpl.class);
	private CassandraTemplate cassandraTemplate = CassandraJunjieConfig.getInstance();
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
		ResultSet resultSet = cassandraTemplate.execute("select count(*) from "+JunjieFormConstants.DEFAULT_KEY_SPACE+"."+JunjieFormConstants.T_HTML_FORM);
		return resultSet.one().getLong(0);
	}
	
}
