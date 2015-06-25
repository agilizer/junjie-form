package com.agilemaster.findoil.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.agilemaster.findoil.domain.Product.ProductStatus;
import com.agilemaster.findoil.service.ArticleService;
import com.agilemaster.findoil.service.ProductService;
import com.agilemaster.findoil.service.UserService;

@Controller
public class HomeController {
	@Autowired
	UserService userService;
	@Autowired
	ArticleService articleService;
	@Autowired
	ProductService productService;
	@RequestMapping("/")
	public String index(Model model,HttpServletRequest request) {
		model.addAttribute("articleJdbcPage", articleService.homeIndex());
		Map<String, Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("productStatus", ProductStatus.ONLINE);
		model.addAttribute("productJdbcPage", productService.list(8,0,parameterMap));
		return "home";
	}
}
