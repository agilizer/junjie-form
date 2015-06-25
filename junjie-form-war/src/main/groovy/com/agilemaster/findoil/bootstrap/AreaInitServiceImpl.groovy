package com.agilemaster.findoil.bootstrap;

import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

import com.agilemaster.findoil.domain.Area
import com.agilemaster.findoil.repository.AreaRepository
import com.agilemaster.share.service.JpaShareService
import com.alibaba.fastjson.JSONArray

@Service
public class AreaInitServiceImpl implements AreaInitService,
ApplicationContextAware {

	@Autowired
	AreaRepository areaRepository;
	@Autowired
	JpaShareService jpaShareService;
	ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
	throws BeansException {
		this.applicationContext =  applicationContext;
	}

	public void init() {
		createArea();
	}
	private void createArea() {
		if (areaRepository.count() > 0){
			return;
			}
			
		Resource template = applicationContext.getResource("classpath:area-json.txt");
		JSONArray array = com.alibaba.fastjson.JSON.parseArray(template.getFile().getText())
		StringBuffer sql=new StringBuffer("insert  into `area`(`id`,`name`,`level`,`parent_id`) "+
		"values (100000,'中国',0,null)")
		array.each {province->
			sql.append(",(${province.id},'${province.name}',1,null)");
			province.cityList.each{city->
				sql.append(",(${city.id},'${city.name}',2,${city.parentId})");
				city.countryList.each{country->
					sql.append(",(${country.id},'${country.name}',3,${country.parentId})");
					country.townList.each{town->
						sql.append(",(${town.id},'${town.name}',3,${town.parentId})");
					}
				}
			}
		}
		jpaShareService.executeNativeSql(sql.toString())
	}
		
}
