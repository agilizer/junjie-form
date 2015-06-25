package com.agilemaster.findoil.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.agilemaster.findoil.service.CommentService;
@Controller
@RequestMapping("/comment")
public class CommentController {
	private static final Logger log = LoggerFactory
			.getLogger(CommentController.class);
	@Autowired
	CommentService commentService;
	
	@RequestMapping("/productList/{productId}/{max}/{offset}")
	public ModelAndView productList(@PathVariable("max")Long productId,@PathVariable("max")Integer max,
			@PathVariable("offset")Integer offset) {
		ModelAndView model = new ModelAndView();
		
		return model;
	}
	@ResponseBody
	@RequestMapping("/product")
	public Map<String,Object> product(Long productId,String content,String star) {
		return commentService.product(productId, content, star);
	}
	
	

}
