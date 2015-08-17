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
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.domain.AnswerCache;
import com.agilemaster.form.util.MD5Util;
import com.datastax.driver.core.querybuilder.Clause;

public class AnswerCacheOptionsImpl implements AnswerCacheOptions{

	private static final Logger log = LoggerFactory
			.getLogger(AnswerCacheOptionsImpl.class);
	private CassandraTemplate cassandraTemplate = CassandraJunjieConfig.getInstance();

	@Override
	public AnswerCache save(AnswerCache domain) {
		return cassandraTemplate.save(domain);
	}

	@Override
	public AnswerCache delete(AnswerCache domain) {
		cassandraTemplate.delete(domain);
		return domain;
	}

	@Override
	public void delete(String id) {
		cassandraTemplate.deleteById(AnswerCache.class, id);		
	}

	@Override
	public boolean update(String id, Map<String, Object> params) {
		List<Clause> whereList = new ArrayList<Clause>();
		whereList.add(eq("id",id));
		cassandraTemplate.update(JunjieFormConstants.T_ANSWER_CACHE, params, whereList);
		return true;
	}

	@Override
	public AnswerCache findOne(Object id) {
		return cassandraTemplate.getEntity(AnswerCache.class, id);
	}

	@Override
	public long count() {
		return 0;
	}

	@Override
	public AnswerCache save(String saasId, String formId, String answerId,String cacheContent) {
		AnswerCache answerCache = new AnswerCache();
		String id  = MD5Util.MD5(saasId+formId+answerId);
		answerCache.setId(id);
		answerCache.setStartAnswerTime(new Date());
		answerCache.setFormRenderCache(cacheContent);
		return save(answerCache);
	}

}
