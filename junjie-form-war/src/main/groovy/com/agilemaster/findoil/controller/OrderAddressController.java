package com.agilemaster.findoil.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.OrderAddress;
import com.agilemaster.findoil.domain.Role;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.service.OrderAddressService;
import com.agilemaster.findoil.service.UserService;
import com.agilemaster.findoil.util.StaticMethod;


@Controller
@RequestMapping("/orderAddress")
public class OrderAddressController {
	
	@Autowired
	OrderAddressService orderAddressService;
	
	@Autowired
	UserService userService;
	@Secured({Role.ROLE_ADMIN,Role.ROLE_USER}) 
	@RequestMapping("/userList")
	public ModelAndView list(){
		User user = userService.currentUser();
		ModelAndView model = new ModelAndView();
		model.addObject("orderAddressList", orderAddressService.list(user));
		model.setViewName("orderAddress/userList");
		return model;
	}
	@Secured({Role.ROLE_ADMIN,Role.ROLE_USER}) 
	@ResponseBody
	@RequestMapping("/update")
	public Map<String,Object> update(Long pk,String name,String value){
		Map<String,Object> result = orderAddressService.updateFiled(pk, name, value);
		return result;
	}
	@Secured({Role.ROLE_ADMIN,Role.ROLE_USER}) 
	@ResponseBody
	@RequestMapping("/save")
	public Map<String,Object> save(OrderAddress orderAddress){
		orderAddress = orderAddressService.save(orderAddress);
		Map<String,Object> result = StaticMethod.genResult();
		if(orderAddress!=null){
			result.put(FindOilConstants.DATA, orderAddress.getId());
			result.put(FindOilConstants.SUCCESS, true);
		}
		return result;
	}

}
