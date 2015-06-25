package com.agilemaster.findoil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agilemaster.findoil.domain.Area;
import com.agilemaster.findoil.service.AreaService;

@Controller
@RequestMapping("/area")
public class AreaController {

	@Autowired()
	AreaService areaService;
	@RequestMapping("/list")
	@ResponseBody
	public List<Area> list(Long parentId) {
		return areaService.listByParentId(parentId);
	}
}
