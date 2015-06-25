package com.agilemaster.findoil.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.agilemaster.findoil.service.AlipayService;

@Controller
@RequestMapping("/alipayBack")
public class AlipayBackController {
	@Autowired
	AlipayService alipayService ;
	@RequestMapping("/alipayNotify")
	public String alipayNotify(Model model,HttpServletRequest request) {
		String result = alipayService.orderPaymentBack(request, AlipayService.TYPE_NOTIFY);
		model.addAttribute("result", result);
		return "alipayBack/result";
	}
	@RequestMapping("/alipayReturn")
	public String alipayReturn(Model model,HttpServletRequest request) {
		String result = alipayService.orderPaymentBack(request, AlipayService.TYPE_RETURN);
		String[] split = result.split("-");
        Long orderId = Long.parseLong(split[0]);
		return "redirect:/order/show/"+orderId;
	}
}
