package com.agilemaster.findoil.service;

import java.util.List;

import com.agilemaster.findoil.domain.Area;

public interface AreaService {
	
	
	public List<Area> getAreaList(int level);
	
	public List<Area> listByParentId(Long parentId);
}
