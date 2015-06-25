package com.agilemaster.findoil.service;

import javax.servlet.ServletContext

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.agilemaster.findoil.consants.FindOilConstants
import com.agilemaster.findoil.domain.ConfigDomain
import com.agilemaster.findoil.repository.ConfigDomainRepository
import com.agilemaster.findoil.util.StaticMethod


@Service
public class ConfigDomainServiceImpl implements ConfigDomainService{
	private static final Logger log = LoggerFactory
			.getLogger(ConfigDomainServiceImpl.class);
	@Autowired
	ServletContext servletContext;
	@Autowired
	ConfigDomainRepository configDomainRepository;
	
	@Override
	public List<ConfigDomain> list() {
		return configDomainRepository.findAll();
	}

	@Override
	public int getConfigInt(String configName) {
		return (int)servletContext.getAttribute(configName);
	}

	@Override
	public boolean getConfigBoolean(String configName) {
		return (boolean)servletContext.getAttribute(configName);
	}

	@Override
	public String getConfigString(String configName) {
		return (String)servletContext.getAttribute(configName);
	}

	@Override
	public Date getConfigDate(String configName) {
		return (Date)servletContext.getAttribute(configName);
	}

	@Override
	public Map<String, Object> update(Long id, String value) {
		ConfigDomain configDomain = configDomainRepository.findOne(id);
		Map<String, Object> result = StaticMethod.genResult()
		if(configDomain!=null){
			configDomain.setMapValue(value);
			StaticMethod.addToConfigMap(servletContext.getAttribute(APPLICATION_KEY_NAME), configDomain)
			configDomainRepository.save(configDomain)
			result.put(FindOilConstants.SUCCESS, true)
		}else{
			result.put(FindOilConstants.ERROR_MSG, "没有找到相关配置项！")
		}
		return result
	}
}
