package com.agilemaster.findoil.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.ArticleCatalog;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.repository.ArticleCatalogRepository;
import com.agilemaster.share.service.JpaShareService;

@Service
public class ArticleCatalogInitService {
	@Autowired
	JpaShareService jpaShareService;
	@Autowired
	ArticleCatalogRepository articleCatalogRepository;
	@Autowired
	ArticleInitService articleInitService;
	public void init(User user) {
		if(articleCatalogRepository.count()==0){
			 ArticleCatalog catalog = new ArticleCatalog();
			 catalog.setAuthor(user);
			 catalog.setCode(FindOilConstants.CATALOG_MARKET_INFO);
			 catalog.setName("行情资讯");
			 
			 
			 articleCatalogRepository.save(catalog);
			 catalog = new ArticleCatalog();
			 catalog.setAuthor(user);
			 catalog.setCode(FindOilConstants.CATALOG_STATIC_PAGE);
			 catalog.setName("网站其它静态内容");
			 articleCatalogRepository.save(catalog);
		}
	}	
}
