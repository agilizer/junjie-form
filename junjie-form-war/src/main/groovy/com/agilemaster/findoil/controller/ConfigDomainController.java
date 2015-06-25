package com.agilemaster.findoil.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.agilemaster.findoil.domain.Role;
import com.agilemaster.findoil.service.ConfigDomainService;
@Secured({Role.ROLE_ADMIN}) 
@Controller
@RequestMapping("/configDomain")
public class ConfigDomainController {
	@Autowired
	ConfigDomainService configDomainService;
	@RequestMapping(value = "/list")
	public String list(Model model) {
		model.addAttribute("configDomainList", configDomainService.list());
		return "configDomain/list";
	}
	@ResponseBody
	@RequestMapping(value = "/update")
	public Map<String, Object> update(Long pk,String value) {
		return configDomainService.update(pk, value);
	}
}
