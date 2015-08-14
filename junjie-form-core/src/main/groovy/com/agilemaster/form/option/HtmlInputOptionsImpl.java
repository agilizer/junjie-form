package com.agilemaster.form.option;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.cassandra.CassandraJunjieConfig;
import com.agilemaster.cassandra.option.CassandraTemplate;
import com.agilemaster.form.constants.FormCoreStaticMethod;
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.HtmlInputAccessor;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Clause;

public class HtmlInputOptionsImpl implements HtmlInputOptions{
	private static final Logger log = LoggerFactory
			.getLogger(HtmlInputOptionsImpl.class);
	private CassandraTemplate cassandraTemplate = CassandraJunjieConfig
			.getInstance();
	@Override
	public HtmlInput save(HtmlInput domain) {
		if(null!=domain){
			Date now = new Date();
			domain.setDateCreated(now);
			domain.setLastUpdated(now);
			if(null==domain.getId()){
				domain.setId(FormCoreStaticMethod.genUUID());
			}
			cassandraTemplate.save(domain);
		}else{
			throw new NullPointerException("save HtmlInput is null!");
		}
		return domain;
	}

	@Override
	public HtmlInput delete(HtmlInput domain) {
		cassandraTemplate.delete(domain);
		return domain;
	}

	@Override
	public void delete(String id) {
		cassandraTemplate.deleteById(HtmlInput.class,id);
	}

	@Override
	public boolean update(String id, Map<String, Object> params) {
		List<Clause> whereList = new ArrayList<Clause>();
		whereList.add(eq("id", id));
		cassandraTemplate.update(JunjieFormConstants.T_HTML_INPUT, params,
				whereList);
		return true;
	}

	@Override
	public HtmlInput findOne(Object id) {
		return cassandraTemplate.getEntity(HtmlInput.class, id);
	}

	@Override
	public long count() {
		ResultSet resultSet = cassandraTemplate.execute("select count(*) from "
				+ JunjieFormConstants.DEFAULT_KEY_SPACE + "."
				+ JunjieFormConstants.T_HTML_INPUT);
		return resultSet.one().getLong(0);
	}

	@Override
	public void deleteByFormId(String formId) {
		ResultSet resultSet = cassandraTemplate.execute("select id from "
				+ JunjieFormConstants.DEFAULT_KEY_SPACE + "."
				+ JunjieFormConstants.T_HTML_INPUT +" where formId = ?",formId);
		List<Row> result = resultSet.all();
		if(null!=result&&result.size()>0){
			for(Row row:result){
				delete(row.getString(0));
			}
		}
	}

	@Override
	public List<HtmlInput> listByFormId(String formId) {
		HtmlInputAccessor htmlInputAccessor = cassandraTemplate.getAccessorMapper(HtmlInputAccessor.class);
		return htmlInputAccessor.listByFormId(formId).all();
	}

	@Override
	public int copyHtmlInputs(String oldFormId, String newFormId) {
		List<HtmlInput> inputs = listByFormId(oldFormId);
		int result = 0;
		for(HtmlInput input:inputs){
			input.setId(newFormId);
			save(input);
			result++;
		}
		log.info("oldFormId {}   newFormId {}  copy htmlInput count {}",oldFormId,newFormId,result);
		return result;
	}
}
