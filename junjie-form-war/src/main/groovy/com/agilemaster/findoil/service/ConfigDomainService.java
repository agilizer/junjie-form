package com.agilemaster.findoil.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.agilemaster.findoil.domain.ConfigDomain;

public interface ConfigDomainService {
	String APPLICATION_KEY_NAME="configMap";
	/**
	 * 
	 * @param id configDomain id
	 * @param value date:yyyy-MM-dd; boolean true or false.
	 */
	Map<String, Object> update(Long id,String value);
	List<ConfigDomain> list();
	int getConfigInt(String configName);
	boolean getConfigBoolean(String configName);
	String getConfigString(String configName);
	Date getConfigDate(String configName);
}
