package com.agilemaster.findoil.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.Invoice;
import com.agilemaster.findoil.domain.Role;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.service.InvoiceService;
import com.agilemaster.findoil.service.UserService;
import com.agilemaster.findoil.util.StaticMethod;


@Controller
@RequestMapping("/invoice")
public class InvoiceController {
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	UserService userService;
	@Secured({Role.ROLE_ADMIN,Role.ROLE_USER}) 
	@RequestMapping("/userList")
	public ModelAndView userList(){
		User user = userService.currentUser();
		ModelAndView model = new ModelAndView();
		model.addObject("invoiceList", invoiceService.list(user));
		model.setViewName("invoice/userList");
		return model;
	}
	@Secured({Role.ROLE_ADMIN,Role.ROLE_USER}) 
	@ResponseBody
	@RequestMapping("/update")
	public Map<String,Object> update(Long pk,String name,String value){
		Map<String,Object> result = invoiceService.updateFiled(pk, name, value);
		return result;
	}
	@Secured({Role.ROLE_ADMIN,Role.ROLE_USER}) 
	@ResponseBody
	@RequestMapping("/save")
	public Map<String,Object> save(Invoice invoice){
		invoice = invoiceService.save(invoice);
		Map<String,Object> result = StaticMethod.genResult();
		if(invoice!=null){
			result.put(FindOilConstants.DATA, invoice.getId());
			result.put(FindOilConstants.SUCCESS, true);
		}
		return result;
	}
}
