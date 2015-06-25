package com.agilemaster.findoil.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.agilemaster.findoil.domain.Article;
import com.agilemaster.findoil.domain.ArticleCatalog;
import com.agilemaster.share.service.JdbcPage;

public interface ArticleService {
	
	String FILE_SAVE_PATH="/article/";
	
	public JdbcPage list(int max,int offset);
	public JdbcPage adminList(int max,int offset);
	
	public JdbcPage homeIndex();
	
	Article save(MultipartFile file,List<ArticleCatalog> catalogList,Article article);
	
	Article update(MultipartFile file,List<ArticleCatalog> catalogList,Article article);
	
	public int getMaxSequence();

	List<?> hotArticle();
	
	List<?> recommendArticle();
}
