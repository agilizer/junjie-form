package com.agilemaster.findoil.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.Product;
import com.agilemaster.findoil.domain.Product.ProductStatus;
import com.agilemaster.findoil.domain.ProductCatalog;
import com.agilemaster.findoil.domain.Role;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.service.AreaService;
import com.agilemaster.findoil.service.CommentService;
import com.agilemaster.findoil.service.ProductCatalogService;
import com.agilemaster.findoil.service.ProductChangeService;
import com.agilemaster.findoil.service.ProductService;
import com.agilemaster.findoil.service.ProductUpdateService;
import com.agilemaster.findoil.service.StorageLocationService;
import com.agilemaster.findoil.service.UserService;
import com.agilemaster.findoil.util.StaticMethod;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;
	@Autowired
	ProductUpdateService productUpdateService;
	@Autowired
	ProductChangeService productChangeService;
	
	@Autowired
	ProductCatalogService productCatalogService;
	
	@Autowired
	StorageLocationService storageLocationService;
	  
	@Autowired
	UserService userService;
	
	@Autowired
	AreaService areaService;
	
	@Autowired
	CommentService commentService;
	
	/**
	 * 展示已经通过审核online的商品
	 * @param toPageNum
	 * @return
	 */
	@RequestMapping("/list/{max}/{offset}")
	public ModelAndView list(@PathVariable("max")Integer max,
			@PathVariable("offset")Integer offset,String storageLocationId,String madeFactory
			,Long[] productCatalogIds,Long[] areaIds) {
		ModelAndView model = new ModelAndView();
		Map<String, Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("storageLocationId", storageLocationId);
		parameterMap.put("madeFactory", madeFactory);
		parameterMap.put("productCatalogIds", productCatalogIds);
		parameterMap.put("areaIds", areaIds);		
		//这句要在生成分页参数之后,否则报错.
		parameterMap.put("productStatus", ProductStatus.ONLINE);
		model.addObject("jdbcPage", productService.list(max,offset,parameterMap));
		String paramsUrl = StaticMethod.genGetUrl(parameterMap);
		model.addObject("paramsUrl",paramsUrl);
		model.addObject("productCatalogList",productCatalogService.getProductCatalogList());
		model.addObject("areaList",areaService.getAreaList(FindOilConstants.AREA_LEVEL_PROVINCE));
		if(productCatalogIds==null){
			productCatalogIds = new Long[0];
		}
		if(areaIds==null){
			areaIds = new Long[0];
		}
		model.addObject("productCatalogIds",productCatalogIds);
		model.addObject("areaIds",areaIds);
		model.setViewName("product/list");
		return model;
	}
	
	/**
	 * 展示所有的product
	 * @param toPageNum
	 * @return
	 */
	@RequestMapping("/resource/list/{max}/{offset}")
	public ModelAndView resourceList(@PathVariable("max")Integer max,
			@PathVariable("offset")Integer offset,Long storageLocationId,String madeFactory
			,Long productCatalogId) {
		ModelAndView model = new ModelAndView();
		Map<String, Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put(ProductService.QUERY__KEY_STORAGE_LOCATION_ID, storageLocationId);
		parameterMap.put(ProductService.QUERY__KEY_MADE_FACTORY, madeFactory);
		parameterMap.put(ProductService.QUERY__KEY_CATALOG_ID, productCatalogId);	
		model.addObject("paramsUrl",StaticMethod.genGetUrl(parameterMap));
		model.addObject("jdbcPage", productService.resourceList(max,offset,parameterMap));
		model.addObject("productCatalogList",productCatalogService.getProductCatalogList());
		model.addObject("storageLocationList",storageLocationService.getStorageLocationList());
		model.addObject("madeFactory",madeFactory);
		model.setViewName("product/resourceList");
		return model;
	}
	
	
	
	/**
	 * 展示需要审查的,产品列表,
	 * @return
	 */
	@RequestMapping("/check/list/{max}/{offset}")
	public ModelAndView checkList(@PathVariable("max")Integer max,
			@PathVariable("offset")Integer offset){
		ModelAndView model = new ModelAndView();
		model.addObject("jdbcPage", productService.list(max,offset,ProductStatus.CHECK));
		model.setViewName("product/checkList");
		return model;
	}
	
	/**
	 * 等待审核的路径
	 * @param id
	 * @param result
	 * @return
	 */
	@Secured({Role.ROLE_ADMIN}) 
	@ResponseBody
	@RequestMapping("/check/{id}")
	public Boolean check(@PathVariable("id") Long id,Boolean result){
		User user = userService.currentUser();
		return productService.check(id, result,user);
	}
	
	@ResponseBody
	@RequestMapping("/listCatalog/{parentId}")
	public List<Map<Object,Object>> listCatalog(@PathVariable("parentId") Long parentId){
		List<Map<Object,Object>> resultList = new ArrayList();
		List<ProductCatalog> catalogs =  productCatalogService.getProductCatalog(parentId);
		Map<Object,Object> temp ;
		for(ProductCatalog catalog:catalogs){
			temp = new HashMap<Object,Object>();
			temp.put("id", catalog.getId());
			temp.put("text", catalog.getName());
			resultList.add(temp);
		}
		return resultList;
	}
	
	@RequestMapping("/save")
	public ModelAndView save(MultipartFile analysisReportFile,Product product,HttpServletRequest request) {
		MultipartFile mainPhoto = null;
		productService.save( analysisReportFile,mainPhoto , product, request);
		return new ModelAndView("redirect:/product/userList/20/0");
	}
	
	@RequestMapping("/update")
	public ModelAndView update(MultipartFile analysisReportFile,Product product,HttpServletRequest request) {
		productUpdateService.update( analysisReportFile, product, request);
		return new ModelAndView("redirect:/product/show/"+product.getId());
	}
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/create")
	public ModelAndView create(HttpServletResponse response){
		response.setHeader("X-Frame-Options", "GOFORIT");
		ModelAndView model = new ModelAndView();
		List<ProductCatalog> rootCatalog = productCatalogService.getProductCatalog(null);
		model.addObject("rootCatalogList",rootCatalog);
		//model.addObject("storageLocationList",storageLocationService.getStorageLocationList());
		model.addObject("areaList",areaService.getAreaList(FindOilConstants.AREA_LEVEL_PROVINCE));
		model.setViewName("product/create");
		return model;
	}
	
	@RequestMapping("/show/{id}")
	public ModelAndView show(@PathVariable("id") Long id) {
		ModelAndView model = new ModelAndView();
		User user = userService.currentUser();
		boolean isComment = false;
		Product product = productService.getDetail(id);
		if(user!=null&&product!=null){
			isComment = commentService.checkComment(product, user);
		}
		if(product==null){
			model.setViewName("404");
		}else{
			model.addObject("product", product);
			model.addObject("productCatalogList",productCatalogService.getProductCatalogList());
			model.addObject("storageLocationList",storageLocationService.getStorageLocationList());
			model.addObject("isComment", isComment);
			model.addObject("comments", commentService.listByProduct(id, 10, 0));
			model.addObject("productCatalogIds",new Long[0]);
			model.setViewName("product/show");
		}
		
		return model;
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id, ModelAndView model) {
		User user = userService.currentUser();
		Product product = productService.getDetail(id);
		if(product==null||(product.getUser()!=user)){
			model.setViewName("404");
		}else{
			model.addObject("product", product);
			List<ProductCatalog> rootCatalog = productCatalogService.getProductCatalog(null);
			model.addObject("rootCatalogList",rootCatalog);
			model.addObject("storageLocationList",storageLocationService.getStorageLocationList());
			model.addObject("areaList",areaService.getAreaList(FindOilConstants.AREA_LEVEL_PROVINCE));
			model.setViewName("product/edit");
		}
		return model;
	}
	
	/**
	 * 获取当前登录用户所创建的resource列表
	 * @param max
	 * @param offset
	 * @param storageLocationId
	 * @param madeFactory
	 * @param productCatalogId
	 * @return
	 */
	@RequestMapping("/userList/{max}/{offset}")
	public ModelAndView userList(@PathVariable("max")Integer max,
			@PathVariable("offset")Integer offset) {
		ModelAndView model = new ModelAndView();
		model.addObject("jdbcPage", productService.listByUser(max,offset));
		model.setViewName("product/userList");
		return model;
	}
	
	@ResponseBody
	@RequestMapping("/deleteFile")
	public Map<String, Object> deleteFile( Long productId, Long fileId,Boolean mainPhoto) {
		return productUpdateService.delFile(productId, fileId, mainPhoto);
	}
	@ResponseBody
	@RequestMapping("/addToCarousel")
	public Map<String, Object> addToCarousel( Long productId, Long fileId) {
		return productUpdateService.addToCarousel(productId, fileId);
	}
	@ResponseBody
	@RequestMapping("/removeFromCarousel")
	public Map<String, Object> removeFromCarousel( Long productId, Long fileId) {
		return productUpdateService.removeFromCarousel(productId, fileId);
	}
	

}
