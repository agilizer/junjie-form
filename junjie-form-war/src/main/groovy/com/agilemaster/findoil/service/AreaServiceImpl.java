package com.agilemaster.findoil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.domain.Area;
import com.agilemaster.findoil.repository.AreaRepository;
import com.agilemaster.share.service.JpaShareService;

@Service
public class AreaServiceImpl implements AreaService {
	
	
	@Autowired
	AreaRepository areaRepository;
	
	@Autowired
	JpaShareService jpaShareService;

	@SuppressWarnings("unchecked")
	@Override
	public List<Area> listByParentId(Long parentId) {
		String hql = "FROM Area a where a.parent.id="+parentId;
		return (List<Area>) jpaShareService.queryForHql(hql,null);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Area> getAreaList(int level) {
		String hql = "FROM Area a where level="+level;
		return (List<Area>) jpaShareService.queryForHql(hql,null);
	}

}
