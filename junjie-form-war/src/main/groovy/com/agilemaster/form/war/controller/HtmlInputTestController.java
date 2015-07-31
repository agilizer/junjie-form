package com.agilemaster.form.war.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/htmlInputTest")
public class HtmlInputTestController {
	@RequestMapping("/index")
	public String index(){
		return "htmlInputTest/index";
	}
	
	@RequestMapping("/create")
	public String create(){
		return "htmlInputTest/create";
	}
	@RequestMapping("/edit")
	public String edit(){
		return "htmlInputTest/edit";
	}
	@RequestMapping("/list")
	public String list(){
		return "htmlInputTest/create";
	}
}
