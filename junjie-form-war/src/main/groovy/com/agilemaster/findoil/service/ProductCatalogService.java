package com.agilemaster.findoil.service;

import java.util.List;
import java.util.Map;

import com.agilemaster.findoil.domain.ProductCatalog;

public interface ProductCatalogService {
	
	/**
	 * 获取第一级分类
	 * @return
	 */
	public List<ProductCatalog>	getProductCatalogList();
	
	public List<ProductCatalog> getProductCatalog(Long parentId);
	
	Map<String,Object>  create(Long parentId,String name);
	
	Map<String,Object>  delete(Long id);
	
	Map<String,Object>  update(Long id,String name);
	
	/**
	 * 
	 * @param parentId
	 * @param names 以空格分隔
	 * @return
	 */
	Map<String,Object>  createMutil(Long parentId,String names);
	Map<String,Object> genZtreeNode(Long id,Long pid,String name,boolean open);
	
	
}
