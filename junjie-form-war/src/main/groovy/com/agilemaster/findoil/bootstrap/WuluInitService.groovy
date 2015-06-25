package com.agilemaster.findoil.bootstrap

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.agilemaster.findoil.domain.WuluType
import com.agilemaster.findoil.repository.WuluTypeRepository

@Service
class WuluInitService {
	@Autowired
	WuluTypeRepository wuluTypeRepository;
	
	void init(){
		WuluType wuluType = null;
		if(null==wuluTypeRepository.findByName("顺丰")){
			wuluType = new WuluType();
			wuluType.setName("顺丰");
			wuluType.setSearchUrl("http://www.sf-express.com/cn/");
			wuluTypeRepository.save(wuluType);
		}
		
		if(null==wuluTypeRepository.findByName("圆通")){
			wuluType = new WuluType();
			wuluType.setName("圆通");
			wuluType.setSearchUrl("http://www.yto.net.cn/gw/index/index.html");
			wuluTypeRepository.save(wuluType);
		}
		if(null==wuluTypeRepository.findByName("申通")){
			wuluType = new WuluType();
			wuluType.setName("申通");
			wuluType.setSearchUrl("http://www.sto.cn/");
			wuluTypeRepository.save(wuluType);
		}
		
	}

}
