package com.agilemaster.findoil.bootstrap

import javax.servlet.ServletContext

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.agilemaster.findoil.consants.FindOilConstants
import com.agilemaster.findoil.domain.ConfigDomain
import com.agilemaster.findoil.domain.ConfigDomain.ValueType
import com.agilemaster.findoil.repository.ConfigDomainRepository
import com.agilemaster.findoil.service.ConfigDomainService
import com.agilemaster.findoil.util.StaticMethod

@Service
class ConfigDomainInitService {
	@Autowired
	ServletContext servletContext;
	@Autowired
	ConfigDomainRepository configDomainRepository;
	void init(){
		initDefaultConfig()
		def map = [:]
		configDomainRepository.findAll().each {
			StaticMethod.addToConfigMap(map, it)	
		}
		servletContext.setAttribute(ConfigDomainService.APPLICATION_KEY_NAME, map)
	}
	private void initDefaultConfig(){
		def configDomain
		if(!configDomainRepository.findByMapName(FindOilConstants.CON_SITE_NAME)){
			saveCon(FindOilConstants.CON_SITE_NAME,"FindOil"
				,true,"网站名称",ValueType.String)
		}
		if(!configDomainRepository.findByMapName(FindOilConstants.CON_SITE_URL)){
			saveCon(FindOilConstants.CON_SITE_URL,"http://localhost:9099/"
				,true,"网站url",ValueType.String)
		}
		if(!configDomainRepository.findByMapName(FindOilConstants.CON_SMS_FINDPW_TEMPLATE)){
			saveCon(FindOilConstants.CON_SMS_FINDPW_TEMPLATE,FindOilConstants.SMS_FINDPW_NOTE
				,true,"找回密码时短信模板",ValueType.String)
		}
		
		if(!configDomainRepository.findByMapName(FindOilConstants.CON_SMS_REGISTER_TEMPLATE)){
			saveCon(FindOilConstants.CON_SMS_REGISTER_TEMPLATE,FindOilConstants.SMS_REGISTER_NOTE
				,true,"注册时短信模板",ValueType.String)
		}
		if(!configDomainRepository.findByMapName(FindOilConstants.CON_FOOTER_KEY)){
			saveCon(FindOilConstants.CON_FOOTER_KEY,"""
		<div class="link">
			<a target="_blank" href="#">关于我们</a> | <a target="_blank" href="#">法律声明</a>
			| <a target="_blank" href="#">诚聘英才</a> | <a target="_blank" href="#">联系我们</a>
			| <a target="_blank" href="#">友情链接</a>
		</div>
		<div class="text">COPYRIGHT &copy; 觅油网 www.findoil.com 京ICP备 XXXXXXX</div>
"""
				,true,"页面底部代码",ValueType.HTML)
		}
		
		
	}
	private void saveCon(String mapName,String mapValue,boolean editAble,String desc,ValueType valueType){
		def configDomain = new ConfigDomain()
		configDomain.setEditable(editAble)
		configDomain.setDescription(desc)
		configDomain.setMapName(mapName)
		configDomain.setMapValue(mapValue);
		configDomain.setValueType(valueType)
		configDomainRepository.saveAndFlush(configDomain)
	}
	
}
