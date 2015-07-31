package com.agilemaster.form.option;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.cassandra.CassandraJunjieConfig;
import com.agilemaster.cassandra.option.CassandraTemplate;
import com.agilemaster.form.constants.FormCoreStaticMethod;
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.domain.FileInfo;
import com.datastax.driver.core.ResultSet;

public class FileInfoOptionsImpl implements FileInfoOptions{

	private static final Logger log = LoggerFactory
			.getLogger(FileInfoOptionsImpl.class);
	private CassandraTemplate cassandraTemplate = CassandraJunjieConfig.getInstance();
	@Override
	public FileInfo save(FileInfo domain) {
		if(null!=domain){
			if(null==domain.getId()){
				domain.setId(FormCoreStaticMethod.genUUID());
			}
			Date date = new Date();
			domain.setDateCreated(date);
			cassandraTemplate.save(domain);
		}
		return domain;
	}

	@Override
	public FileInfo delete(FileInfo domain) {
		cassandraTemplate.delete(domain);
		return domain;
	}

	@Override
	public void delete(String id) {
		cassandraTemplate.deleteById(FileInfo.class, id);
	}

	/**
	 * no impl
	 */
	@Override
	public boolean update(String id, Map<String, Object> params) {
		return false;
	}

	@Override
	public FileInfo findOne(Object id) {
		return cassandraTemplate.getEntity(FileInfo.class, id);
	}

	@Override
	public long count() {
		ResultSet resultSet = cassandraTemplate.execute("select count(*) from "
				+ JunjieFormConstants.DEFAULT_KEY_SPACE + "."
				+ JunjieFormConstants.T_FILE_INFO);
		return resultSet.one().getLong(0);
	}


}
