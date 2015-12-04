package com.agilemaster.form.war.service;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.cassandra.CassandraJunjieConfig;
import com.agilemaster.cassandra.option.CassandraTemplate;
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.war.WebAppConfig;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

@Service
public class AuthServiceImpl implements AuthService {
	private static final Logger log = LoggerFactory
			.getLogger(AuthServiceImpl.class);
	@Autowired
	CassandraTemplate cassandraTemplate;

	@Override
	public boolean auth(ServletRequest req) {
		boolean result = false;
		String saasId = "";
		String accessKey = req.getParameter("accessKey");
		if (null != accessKey) {
			ResultSet resultSet = cassandraTemplate.execute(
					"select id from "
							+ CassandraJunjieConfig.getKeySpace() + "."
							+ JunjieFormConstants.T_FORM_SAAS + " where accessKey=?",
							accessKey);
			Row row = resultSet.one();
			if (null != row) {
				result = true;
				saasId = row.getString(0);
				req.setAttribute("saasId", saasId);
			}
		}
		log.info("auth saasId:{},accessKey:{}  result {}", saasId, accessKey,
				result);
		return result;
	}

}
