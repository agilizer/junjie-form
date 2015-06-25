package com.agilemaster.findoil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.domain.ArticleCatalog;
import com.agilemaster.findoil.repository.ArticleCatalogRepository;
@Service
public class ArticleCatalogServiceImpl implements ArticleCatalogService {

	@Autowired
	ArticleCatalogRepository articleCatalogRepository;  
	@Override
	public List<ArticleCatalog> list() {
		return articleCatalogRepository.findAll();
	}

}
