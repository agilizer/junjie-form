package com.agilemaster.findoil.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.agilemaster.findoil.domain.Article;
import com.agilemaster.findoil.domain.ArticleCatalog;
import com.agilemaster.findoil.domain.Role;
import com.agilemaster.findoil.repository.ArticleCatalogRepository;
import com.agilemaster.findoil.repository.ArticleRepository;
import com.agilemaster.findoil.service.ArticleCatalogService;
import com.agilemaster.findoil.service.ArticleService;


@Controller
@RequestMapping("/article")
public class ArticleController {
	
	@Autowired
	ArticleService articleService;
	@Autowired
	ArticleRepository articleRepository;
	
	@Autowired
	ArticleCatalogService articleCatalogService;
	
	@Autowired
	ArticleCatalogRepository articleCatalogRepository;
	
	@RequestMapping("/list/{max}/{offset}")
	public ModelAndView list(@PathVariable("max")int max,@PathVariable("offset")int offset){
		ModelAndView model = new ModelAndView();
		model.addObject("jdbcPage", articleService.list(max,offset));
		model.addObject("hotArticles", articleService.hotArticle());
		model.addObject("recommendArticles", articleService.recommendArticle());
		model.setViewName("article/list");
		return model;
	}
	
	@RequestMapping("/adminList/{max}/{offset}")
	public ModelAndView adminIist(@PathVariable("max")int max,@PathVariable("offset")int offset){
		ModelAndView model = new ModelAndView();
		model.addObject("jdbcPage", articleService.adminList(max,offset));
		model.setViewName("article/adminList");
		return model;
	}
	@Secured({Role.ROLE_ADMIN}) 
	@RequestMapping("/create")
	public String create(Model model){
		model.addAttribute("catalogs",articleCatalogService.list());
		model.addAttribute("maxIndexSequence", articleService.getMaxSequence());
		return "article/create";
	}
	
	@Secured({Role.ROLE_ADMIN}) 
	@RequestMapping("/save")
	public String save(MultipartFile file,Long catalogId,Article article,Model model){
		List<ArticleCatalog> catalogList = new ArrayList<ArticleCatalog>();
		catalogList.add(articleCatalogRepository.findOne(catalogId));
		articleService.save(file, catalogList, article);
		model.addAttribute("article",article);
		return "redirect:/article/show/"+article.getId();
	}
	
	
	@Secured({Role.ROLE_ADMIN}) 
	@RequestMapping("/update")
	public String update(MultipartFile file,Long catalogId,Article article,Model model){
		List<ArticleCatalog> catalogList = new ArrayList<ArticleCatalog>();
		catalogList.add(articleCatalogRepository.findOne(catalogId));
		article = articleService.update(file, catalogList, article);
		model.addAttribute("article",article);
		return "article/show";
	}
	
	@RequestMapping("/show/{id}")
	public String show(@PathVariable("id")Long id,Model model){
		model.addAttribute("article",articleRepository.getOne(id));
		return "article/show";
	}
	
	@RequestMapping("/show/c/{code}")
	public String show(@PathVariable("code")String code,Model model){
		model.addAttribute("article",articleRepository.findByCode(code));
		return "article/show";
	}
	
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id")Long id,Model model){
		model.addAttribute("article",articleRepository.getOne(id));
		model.addAttribute("catalogs",articleCatalogService.list());
		model.addAttribute("maxIndexSequence", articleService.getMaxSequence());
		return "article/edit";
	}

}
