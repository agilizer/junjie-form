package com.agilemaster.form.war.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;
@ApiIgnore
@Controller
@RequestMapping("/htmlFormTest")
public class HtmlFormTestController {
	
	@RequestMapping("/index")
	public String index(){
		return "htmlFormTest/index";
	}
	
	@RequestMapping("/create")
	public String create(){
		return "htmlFormTest/create";
	}
	@RequestMapping("/edit")
	public String edit(@ModelAttribute String accessKey,@ModelAttribute String saasId,@ModelAttribute String htmlFormId){
		return "htmlFormTest/edit";
	}
	@RequestMapping("/list")
	public String list(){
		return "htmlFormTest/create";
	}

}
