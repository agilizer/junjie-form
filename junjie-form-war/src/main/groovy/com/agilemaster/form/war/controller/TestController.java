package com.agilemaster.form.war.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import springfox.documentation.annotations.ApiIgnore;

import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.option.FormSaasOptions;


@ApiIgnore
@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	FormSaasOptions formSaasOptions;
	@ResponseBody
	@RequestMapping("/t")
	public FormSaas t() {
		String id = UUID.randomUUID().toString();
		FormSaas formSaas = new FormSaas();
		formSaas.setId(id);
		formSaasOptions.save(formSaas);
		return formSaas;
	}
}
