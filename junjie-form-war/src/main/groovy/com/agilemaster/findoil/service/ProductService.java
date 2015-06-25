package com.agilemaster.findoil.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.agilemaster.findoil.domain.FileInfo;
import com.agilemaster.findoil.domain.Product;
import com.agilemaster.findoil.domain.Product.ProductStatus;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.share.service.JdbcPage;

public interface ProductService {
	String QUERY__KEY_STORAGE_LOCATION_ID = "storageLocationId";
	
	String QUERY__KEY_MADE_FACTORY = "madeFactory";
	String QUERY__KEY_CATALOG_ID = "productCatalogId";
	public JdbcPage list(int max,int offset,ProductStatus productStatus);
	
	public JdbcPage list(int max,int offset,Map<String,Object> parameterMap);
	
	public JdbcPage list(int max,int offset,Map<String,Object> parameterMap,User user);
	
	public JdbcPage resourceList(int max,int offset,Map<String,Object> parameterMap);
	
	public JdbcPage resourceList(Map<String,Object> parameterMap);
	
	public Boolean save(MultipartFile analysisReportFile,MultipartFile mainPhoto,Product product,HttpServletRequest request);
	
	public Product getDetail(Long id);
	
	public Boolean check(Long id,Boolean result,User user);
	
	
	public JdbcPage listByUser(int max,int offset);
	
	
	


}
