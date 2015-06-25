package com.agilemaster.findoil.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.ProductCatalog;
import com.agilemaster.findoil.repository.ProductCatalogRepository;
import com.agilemaster.findoil.util.StaticMethod;
import com.agilemaster.share.service.JpaShareService;

@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {
	private static final Logger log = LoggerFactory
			.getLogger(ProductCatalogServiceImpl.class);
	@Autowired
	ProductCatalogRepository productCatalogRepository;

	@Autowired
	JpaShareService japShareService;

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCatalog> getProductCatalog(Long parentId) {
		String hql = "";
		if (null == parentId) {
			hql = "FROM ProductCatalog p WHERE p.parentCataLog IS NULL";
		} else {
			hql = "FROM ProductCatalog p WHERE p.parentCataLog.id=" + parentId;
		}
		return (List<ProductCatalog>) japShareService.queryForHql(hql, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductCatalog> getProductCatalogList() {
		String hql = "FROM ProductCatalog p WHERE p.parentCataLog IS NULL";
		return (List<ProductCatalog>) japShareService.queryForHql(hql, null);
	}

	@Override
	public Map<String, Object> create(Long parentId, String name) {
		ProductCatalog parentCatalog = productCatalogRepository
				.findOne(parentId);
		Map<String, Object> result = StaticMethod.genResult();
		if (parentCatalog != null && name != null && !name.trim().equals("")) {
			if (checkExist(name,null)) {
				ProductCatalog productCatalog = new ProductCatalog();
				productCatalog.setParentCataLog(parentCatalog);
				productCatalog.setName(name);
				productCatalog.setSequence(productCatalogRepository.maxSequence()+5);
				productCatalog.setOnline(true);
				productCatalogRepository.saveAndFlush(productCatalog);
				japShareService
				.executeNativeSql(
						"insert into product_catalog_children_catalogs(product_catalog_id ,children_catalogs_id )"
						+ "values("+parentId+","+productCatalog.getId()+")");
				result.put(FindOilConstants.DATA, genZtreeNode(productCatalog.getId(),parentId,name,false));
				result.put(FindOilConstants.SUCCESS, true);
			} else {
				result.put(FindOilConstants.ERROR_MSG, "已经存在相同名称的类别！");
			}
		} else {
			result.put(FindOilConstants.ERROR_MSG, "父类别不能为空，名称不能为空！");
		}
		return result;
	}
	public Map<String,Object> genZtreeNode(Long id,Long pid,String name,boolean open){
		Map<String,Object> temp = new HashMap<String,Object>();
		temp.put("id", id);
		temp.put("pId", pid);
		temp.put("dbId", id);
		temp.put("name", "id:"+id+" "+name);
		temp.put("open", open);
		return temp;
		
	}

	/**
	 * 
	 * @param parentId
	 * @param name
	 * @param selfId 如果是更新时则传入id
	 * @return true not exist ,false exist
	 */
	private boolean checkExist(String name,Long selfId){
		String hql = "select p.id FROM ProductCatalog p WHERE p.name=:name ";
		Map<String, Object>  queryParams = new HashMap<String,Object>();
		queryParams.put("name",name);
		if(selfId!=null){
			hql = hql + " and p.id!=:selfId";
			queryParams.put("selfId",selfId);
		}
		List<?> queryResult = japShareService.queryForHql(hql,queryParams);
		return queryResult.size()==0?true:false;
	}

	@Override
	public Map<String, Object> delete(Long id) {
		Map<String, Object> result = StaticMethod.genResult();
		try {
			//TODO  删除时得先删除parentCatalog关系
			japShareService
			.executeNativeSql(
					"delete from  product_catalog_children_catalogs where children_catalogs_id="
					+ id);
			productCatalogRepository.delete(id);
			result.put(FindOilConstants.SUCCESS, true);
		} catch (Exception e) {
			log.error("delete productCatalog "+id+" error:",e);
			result.put(FindOilConstants.ERROR_MSG, "类别下有产品，或者其它参考数据，不能删除！");
		}
		return result;
	}

	@Override
	public Map<String, Object> update(Long id, String name) {
		ProductCatalog productCatalog = productCatalogRepository
				.findOne(id);
		Map<String, Object> result = StaticMethod.genResult();
		if (productCatalog != null && name != null && !name.trim().equals("")) {
			if (checkExist(name,productCatalog.getId())) {
				productCatalog.setName(name);
				productCatalogRepository.save(productCatalog);
				result.put(FindOilConstants.DATA, genZtreeNode(productCatalog.getId(),
						productCatalog.getParentCataLog().getId(),name,false));
				result.put(FindOilConstants.SUCCESS, true);
			} else {
				result.put(FindOilConstants.ERROR_MSG, name+"已经存在相同名称的类别！");
			}
		} else {
			result.put(FindOilConstants.ERROR_MSG, "类别不能为空，名称不能为空！");
		}
		return result;
	}

	@Override
	public Map<String, Object> createMutil(Long parentId, String names) {
		Map<String, Object> result = StaticMethod.genResult();
		StringBuffer errorMsg = new StringBuffer();
		Map<String, Object> tempResult = null;
		for(String name:names.split(" ")){
			if(!name.trim().equals("")){
				tempResult = create(parentId,name);
				if(!(boolean) tempResult.get(FindOilConstants.SUCCESS)){
					errorMsg.append(tempResult.get(FindOilConstants.ERROR_MSG));
				}
			}
		}
		result.put(FindOilConstants.ERROR_MSG, errorMsg.toString());
		result.put(FindOilConstants.SUCCESS, true);
		return result;
	}

}
