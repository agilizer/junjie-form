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
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.querybuilder.Clause;

public class InputValueOptionsImpl implements InputValueOptions{
	private static final Logger log = LoggerFactory
			.getLogger(InputValueOptionsImpl.class);
	private CassandraTemplate cassandraTemplate = CassandraJunjieConfig.getInstance();
	@Override
	public InputValue save(InputValue domain) {
		if (null != domain) {
			if (null == domain.getId()) {
				String id = UUID.randomUUID().toString();
				domain.setId(id);
			}
			Date date = new Date();
			domain.setDateCreated(date);
			try {
				cassandraTemplate.save(domain);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new NullPointerException("save InputValue is null!");
		}
		return domain;
	}

	@Override
	public InputValue delete(InputValue domain) {
		cassandraTemplate.delete(domain);
		return domain;
	}

	@Override
	public void delete(String id) {
		cassandraTemplate.deleteById(InputValue.class,id);
	}

	@Override
	public boolean update(String id, Map<String, Object> params) {
		List<Clause> whereList = new ArrayList<Clause>();
		whereList.add(eq("id", id));
		cassandraTemplate.update(JunjieFormConstants.T_INPUT_VALUE, params,
				whereList);
		return true;
	}

	@Override
	public InputValue findOne(Object id) {
		return cassandraTemplate.getEntity(InputValue.class, id);
	}

	@Override
	public long count() {
		ResultSet resultSet = cassandraTemplate.execute("select count(*) from "
				+ JunjieFormConstants.DEFAULT_KEY_SPACE + "."
				+ JunjieFormConstants.T_INPUT_VALUE);
		return resultSet.one().getLong(0);
	}

	@Override
	public List<?> listByHtmlForm(String htmlFormId) {
		ResultSet resultSet = cassandraTemplate.execute("select answerId,htmlInputId,strValue,answerRight from "
				+ JunjieFormConstants.DEFAULT_KEY_SPACE + "."
				+ JunjieFormConstants.T_INPUT_VALUE +" where formId=?",htmlFormId);
		if(resultSet!=null){
			return resultSet.all();
		}else{
			return null;
		}
		
	}

}
