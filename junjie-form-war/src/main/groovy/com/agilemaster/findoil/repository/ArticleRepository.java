package com.agilemaster.findoil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.agilemaster.findoil.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>{

	@Query("select count(distinct a.id) from Article a join a.catalogs catalog where catalog.id=?1")
	Long countByCatalog(Long catalogId);
	
	Article findByCode(String code);
}
