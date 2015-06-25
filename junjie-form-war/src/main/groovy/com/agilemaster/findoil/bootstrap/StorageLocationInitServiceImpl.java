package com.agilemaster.findoil.bootstrap;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.domain.Area;
import com.agilemaster.findoil.domain.StorageLocation;
import com.agilemaster.findoil.repository.StorageLocationRepository;
import com.agilemaster.share.service.JpaShareService;

@Service
public class StorageLocationInitServiceImpl implements StorageLocationInitService {
	
	@Autowired
	StorageLocationRepository storageLocationRepository;
	
	@Autowired
	JpaShareService jpaShareService;

	public void init() {
		if (storageLocationRepository.count() > 0)
			return;
		Map parameter = new HashMap();
		String hql = "FROM Area a WHERE a.name=:name";
		StorageLocation storageLocation1 = new StorageLocation();
		Calendar calendar = Calendar.getInstance();
		storageLocation1.setAddress("罗春路218号，月罗公路抚远路口往北走到罗春路右拐");
		storageLocation1.setContactTel("021-56562882");
		storageLocation1.setDateCreated(calendar);
		storageLocation1.setName("宝钢运输八号库");
		storageLocation1.setWorkTime("24小时运作");
		parameter.put("name", "河南省");
		storageLocation1.setArea((Area) jpaShareService.queryForHql(hql, parameter).get(0));
		storageLocationRepository.save(storageLocation1);

		StorageLocation storageLocation2 = new StorageLocation();
		storageLocation2.setAddress("长江西路850-880号");
		storageLocation2.setContactTel("021-31377537");
		storageLocation2.setDateCreated(calendar);
		storageLocation2.setName("中板超市");
		storageLocation2.setWorkTime("8：00-19：30");
		parameter.put("name", "河南省");
		storageLocation2.setArea((Area) jpaShareService.queryForHql(hql, parameter).get(0));
		storageLocationRepository.save(storageLocation2);

	
	}	

}
