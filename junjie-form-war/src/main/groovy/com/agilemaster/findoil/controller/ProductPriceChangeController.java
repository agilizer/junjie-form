package com.agilemaster.findoil.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.agilemaster.findoil.domain.Role;
import com.agilemaster.findoil.domain.ProductPriceChange.ChangeStatus;
import com.agilemaster.findoil.service.ProductChangeService;
import com.agilemaster.findoil.service.ProductService;
import com.agilemaster.findoil.service.UserService;
import com.agilemaster.share.service.JdbcPage;

@Controller
@RequestMapping("/productPriceChange")
public class ProductPriceChangeController {
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductChangeService productChangeService;
	
	
	@Autowired
	UserService userService;
	
	@ResponseBody
	@RequestMapping("/submit")
	public Map<String,Object> submit(Long productId,Float changePrice){
		Map<String,Object> result = productChangeService.submitChangePrice(productId, changePrice);
		return result;
	}
	@Secured({Role.ROLE_ADMIN}) 
	@ResponseBody
	@RequestMapping("/check/{id}")
	public Map<String,Object> check(@PathVariable("id")Long id,Boolean result){
		Map<String,Object> returnResult = productChangeService.checkChangePrice(id, result);
		return returnResult;
	}
	@Secured({Role.ROLE_ADMIN}) 
	@RequestMapping("/checkList/{max}/{offset}")
	public ModelAndView checkList(@PathVariable("max")Integer max,
			@PathVariable("offset")Integer offset){
		ModelAndView model = new ModelAndView();
		JdbcPage jdbcPage = productChangeService.checkList(max,offset,ChangeStatus.SUBMIT);
		model.addObject("jdbcPage", jdbcPage);
		model.setViewName("productPriceChange/checkList");
		return model;
		
	}
	

}
