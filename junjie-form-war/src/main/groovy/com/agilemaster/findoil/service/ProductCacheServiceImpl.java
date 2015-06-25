package com.agilemaster.findoil.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.domain.ProductCache;
import com.agilemaster.share.service.JpaShareService;

@Service
public class ProductCacheServiceImpl implements ProductCacheService{
	@Autowired
	JpaShareService jpaShareService;
	
	@Override
	public synchronized void  increaseComment(ProductCache productCache) {
		Map<String,Object> updateMap = new HashMap<String,Object>();
		updateMap.put("id", productCache.getId());
		String hql = "update ProductCache set commentCount = commentCount+1 where id=:id";
		jpaShareService.updateForHql(hql, updateMap);
	}

	@Override
	public synchronized void increaseOrder(ProductCache productCache) {
		Map<String,Object> updateMap = new HashMap<String,Object>();
		updateMap.put("id", productCache.getId());
		String hql = "update ProductCache set sumOrder = sumOrder+1 where id=:id";
		jpaShareService.updateForHql(hql, updateMap);
	}

}
