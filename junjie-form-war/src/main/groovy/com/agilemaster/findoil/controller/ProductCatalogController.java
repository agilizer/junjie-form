package com.agilemaster.findoil.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agilemaster.findoil.domain.ProductCatalog;
import com.agilemaster.findoil.service.ProductCatalogService;

@Controller
@RequestMapping("/productCatalog")
public class ProductCatalogController {
	
	@Autowired
	ProductCatalogService productCatalogService;
	@ResponseBody
	@RequestMapping("/list")
	public List<Map<String,Object>> list() {
		List<Map<String,Object>>  result = new ArrayList<Map<String, Object>>();
		List<ProductCatalog> rootCatalogList = productCatalogService.getProductCatalogList();
		result.add(productCatalogService.genZtreeNode(0l,null,"所有产品类别",true));
		for(ProductCatalog levelTwo:rootCatalogList){
			result.add(productCatalogService.genZtreeNode(levelTwo.getId(),0l,levelTwo.getName(),true));
			addChildrenNodes(result,levelTwo);
		}
		return result;
	}
	private void addChildrenNodes(List<Map<String,Object>>  result,ProductCatalog parentCatalog){
		if(parentCatalog.getChildrenCatalogs().size()>0){
			for(ProductCatalog levelTwo:parentCatalog.getChildrenCatalogs()){
				result.add(productCatalogService.genZtreeNode(levelTwo.getId(),parentCatalog.getId(),levelTwo.getName(),false));
				addChildrenNodes(result,levelTwo);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/create")
	public Map<String,Object> create(Long parentId,String name) {
		name="请输入类别名称";
		return productCatalogService.create(parentId, name);
	}
	
	@ResponseBody
	@RequestMapping("/createMutil")
	public Map<String,Object> createMutil(Long parentId,String catalogNames) {
		return productCatalogService.createMutil(parentId, catalogNames);
	}
	@ResponseBody
	@RequestMapping("/delete/{id}")
	public Map<String,Object> delete(@PathVariable("id")Long id) {
		return productCatalogService.delete(id);
	}
	@ResponseBody
	@RequestMapping("/update")
	public Map<String,Object> update(Long id,String name) {
		name = name.replace("id:"+id+" ", "");
		return productCatalogService.update(id, name);
	}

}
