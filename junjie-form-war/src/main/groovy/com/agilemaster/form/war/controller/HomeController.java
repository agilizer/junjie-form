package com.agilemaster.form.war.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;
@ApiIgnore
@Controller
public class HomeController {

	@RequestMapping("/")
	public String index(){
		return "index";
	}
	@RequestMapping("/doc")
	public String api(){
		return "swagger";
	}
}
