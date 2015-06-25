package com.agilemaster.findoil.service;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.Article;
import com.agilemaster.findoil.domain.ArticleCatalog;
import com.agilemaster.findoil.domain.FileInfo;
import com.agilemaster.findoil.repository.ArticleCatalogRepository;
import com.agilemaster.findoil.repository.ArticleRepository;
import com.agilemaster.share.service.FileService;
import com.agilemaster.share.service.JpaShareService;
import com.agilemaster.share.service.JdbcPage;

@Service
public class ArticleServiceImpl implements ArticleService {
	private static final Logger log = LoggerFactory
			.getLogger(ArticleServiceImpl.class);
	@Autowired
	ArticleRepository articleRepository;
	
	@Autowired
	ArticleCatalogRepository articleCatalogRepository;
	
	@Autowired
	JpaShareService jpaShareService;
	
	@Autowired
	FileService fileService;
	
	@Autowired
	UserService userService;
	
	@Override
	public JdbcPage list(int max,int offset) {
		String hql = "select a.id, a.title,a.metaDesc,fileInfo.storePath"
				+ " ,a.dateCreated,a.showTimes "
				+ " FROM Article a join a.specImage fileInfo join a.catalogs catalog where a.online=true"
				+ " and catalog.code='"+FindOilConstants.CATALOG_MARKET_INFO+"' order by a.id desc ";
		String countHql = "SELECT COUNT(*) FROM Article a ";
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, countHql, max,
				offset,null);
		return jdbcPage;
	}

	@Override
	public Article save(MultipartFile file, List<ArticleCatalog> catalogList, Article article){ 
		FileInfo fileInfo = fileService.saveFile(file, FILE_SAVE_PATH);
		article.setAuthor(userService.currentUser());
		Calendar now = Calendar.getInstance();
		article.setDateCreated(now);
		article.setLastUpdated(now);
		article.setSpecImage(fileInfo);
		article.setCatalogs(catalogList);
		articleRepository.save(article);
		return article;
	}

	@Override
	public JdbcPage homeIndex() {
		String hql = "select a.id, a.title,fileInfo.storePath"
				+ " FROM Article a join a.specImage fileInfo where a.indexShow=true and a.online=true"
				+  "   order by a.indexSequence desc ";
		String countHql = "SELECT COUNT(*) FROM Article a ";
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, countHql, 3,
				0,null);
		return jdbcPage;
	}

	@Override
	public int getMaxSequence() {
		String hql = "select max(indexSequence) from Article  where indexShow = true";
		List<?> result = jpaShareService.queryForHql(hql, null);
		Object maxSequence = 0;
		if(result.get(0)!=null){
			log.info(result.getClass().getName());
			maxSequence =  result.get(0);
		}
		return (int) maxSequence;
	}

	@Override
	public Article update(MultipartFile file, List<ArticleCatalog> catalogList,
			Article article) {
		Article articleOld = articleRepository.getOne(article.getId());
		articleOld.setTitle(article.getTitle());
		articleOld.setCode(article.getCode());
		articleOld.setKeywords(article.getKeywords());
		articleOld.setMetaDesc(article.getMetaDesc());
		articleOld.setOnline(article.getOnline());
		articleOld.setIndexShow(article.getIndexShow());
		articleOld.setIndexSequence(article.getIndexSequence());
		articleOld.setContent(article.getContent());
		FileInfo fileInfo = fileService.saveFile(file, FILE_SAVE_PATH);
		Calendar now = Calendar.getInstance();
		articleOld.setLastUpdated(now);
		if(fileInfo!=null){
			articleOld.setSpecImage(fileInfo);
		}
		articleOld.setCatalogs(catalogList);
		articleRepository.save(articleOld);
		return articleOld;
	}

	@Override
	public List<?> hotArticle() {
		String hql = "select a.id, a.title,a.metaDesc,fileInfo.storePath"
				+ " ,a.dateCreated,a.showTimes "
				+ " FROM Article a join a.specImage fileInfo  join a.catalogs catalog where a.online=true"
				+ " and catalog.code='"+FindOilConstants.CATALOG_MARKET_INFO+ "'  order by a.id desc ";
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, "select count(*) from Article", 6,
				0,null);
		return jdbcPage.getPageItems();
	}

	@Override
	public List<?> recommendArticle() {
		String hql = "select a.id, a.title,a.metaDesc,fileInfo.storePath"
				+ " ,a.dateCreated,a.showTimes "
				+ " FROM Article a join a.specImage fileInfo  join a.catalogs catalog where a.online=true and a.recommend=true"
				+ " and catalog.code='"+FindOilConstants.CATALOG_MARKET_INFO+ "' order by a.id desc ";
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, "select count(*) from Article", 6,
				0,null);
		return jdbcPage.getPageItems();
	}

	@Override
	public JdbcPage adminList(int max, int offset) {
		String hql = "select a.id, a.title,a.metaDesc,fileInfo.storePath"
				+ " ,a.dateCreated,a.showTimes "
				+ " FROM Article a join a.specImage fileInfo  order by a.id desc ";
		String countHql = "SELECT COUNT(*) FROM Article a ";
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, countHql, max,
				offset,null);
		return jdbcPage;
	}

}
