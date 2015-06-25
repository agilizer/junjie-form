package com.agilemaster.findoil.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin")
public class AdminController {
	@RequestMapping("/index")
	public String index(Model model) {
		return "admin/index";
	}
	@RequestMapping("/productCatalog")
	public String productCatalog(Model model) {
		return "admin/productCatalog";
	}
	
	
}
