package com.agilemaster.form.war.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/htmlInputTest")
	public String index(){
		return "index";
		
	}
}
