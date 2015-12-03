package com.agilemaster.form.war.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;
@ApiIgnore
@Controller
@RequestMapping("/statisticsTest")
public class StatisticsTestContorller {

	@RequestMapping("/index")
	public String index(){
		return "statisticsTest/statistics";
	}
}
