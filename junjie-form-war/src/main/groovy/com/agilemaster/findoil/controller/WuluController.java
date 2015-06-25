package com.agilemaster.findoil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agilemaster.findoil.domain.WuluType;
import com.agilemaster.findoil.repository.WuluTypeRepository;

@Controller
@RequestMapping("/wulu")
public class WuluController {
	@Autowired()
	WuluTypeRepository wuluTypeRepository;
	@RequestMapping("/typeList")
	@ResponseBody
	public List<WuluType> typeList() {
		return wuluTypeRepository.findAll();
	}

}
