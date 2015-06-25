package com.agilemaster.findoil.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.agilemaster.findoil.service.SellerService;
import com.agilemaster.findoil.service.UserService;
@Controller
@RequestMapping("/seller")
public class SellerController {

	@Autowired
	UserService userService;
	@Autowired
	SellerService sellerService;
	/**
	 * 查询当前登录用户,所下的所有订单
	 * 
	 * @param max
	 * @param offset
	 * @return
	 */
	@RequestMapping("/userList/{max}/{offset}")
	public ModelAndView userList(@PathVariable("max") Integer max,
			@PathVariable("offset") Integer offset) {
		ModelAndView model = new ModelAndView();
		model.addObject("jdbcPage", sellerService.listOrder(max, offset));
		model.setViewName("seller/userList");
		return model;
	}
	@ResponseBody
	@RequestMapping("/sendGood")
	public Map<String,Object> sendGood(Long orderId,Long wuluTypeId,String wuluId) {
		return sellerService.sendGood(orderId, wuluTypeId, wuluId);
	}
}
