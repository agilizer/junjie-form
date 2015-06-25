package com.agilemaster.findoil.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.Role;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.repository.RoleRepository;
import com.agilemaster.findoil.repository.UserRepository;
import com.agilemaster.findoil.repository.UserRoleRepository;
import com.agilemaster.findoil.service.UserRoleService;
import com.agilemaster.findoil.util.StaticMethod;

@Controller
@RequestMapping("/adminUser")
public class AdminUserController {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserRoleRepository userRoleRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRoleService userRoleService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		model.addAttribute("userList", userRepository.findAll());
		model.addAttribute("roleList", roleRepository.findAll());
		return "adminUser/list";
	}
	
	@ResponseBody
	@RequestMapping("/edit/{id}")
	public Map<String,Object> edit(@PathVariable Long id,Model model) {
		Map<String,Object> result = StaticMethod.genResult();
		if(null!=id){
			List<Role> roleList = userRoleRepository.findRolesByUser(id);
			result.put("roleList", roleList);
			result.put(FindOilConstants.SUCCESS, true);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/addRole")
	public String addRole(Long userId,Long roleId) {
		userRoleService.insert(roleId, userId);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("/removeRole")
	public String removeRole(Long userId,Long roleId) {
		userRoleService.delete(roleId, userId);
		return "success";
	}
	
	
	@ResponseBody
	@RequestMapping("/enabled")
	public String enabled(Long userId,Boolean enabled) {
		userRoleService.enabled(userId, enabled);
		return "success";
	}
	
	
	

}
