package com.agilemaster.findoil.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.dao.ProductDao;
import com.agilemaster.findoil.domain.Area;
import com.agilemaster.findoil.domain.FileInfo;
import com.agilemaster.findoil.domain.Product;
import com.agilemaster.findoil.domain.ProductCatalog;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.repository.AreaRepository;
import com.agilemaster.findoil.repository.FileInfoRepository;
import com.agilemaster.findoil.repository.ProductCatalogRepository;
import com.agilemaster.findoil.repository.ProductRepository;
import com.agilemaster.findoil.repository.StorageLocationRepository;
import com.agilemaster.findoil.repository.UserRepository;
import com.agilemaster.findoil.util.StaticMethod;
import com.agilemaster.share.service.FileService;
import com.agilemaster.share.service.JpaShareService;

@Service
public class ProductUpdateServiceImpl implements ProductUpdateService {
	private static final Logger log = LoggerFactory
			.getLogger(ProductUpdateServiceImpl.class);
	@Autowired
	ProductRepository productRepository;

	@Autowired
	AreaRepository areaRepository;

	@Autowired
	ProductDao productDao;

	@Autowired
	FileInfoRepository fileInfoRepository;

	@Autowired
	ProductCatalogRepository productCatalogRepository;

	@Autowired
	StorageLocationRepository storageLocationRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;

	// 用于分页调用
	@Autowired
	JpaShareService jpaShareService;
	@Autowired
	FileService fileService;

	private String rootPath = "";

	@PostConstruct
	public void init() throws IOException {
		Resource resource = new ClassPathResource("application.properties");
		rootPath = resource.getFile().getParentFile().getAbsolutePath()
				+ "/files/upload";
	}

	@Override
	public Map<String, Object> delFile(Long productId, Long fileId,
			Boolean mainPhoto) {
		Map<String, Object> result = StaticMethod.genResult();
		if (productId != null) {
			if (mainPhoto) {
				jpaShareService.executeNativeSql(
						"update product set main_photo_id = null where id=?",
						productId);
			}
			String delSql = "delete from   product_images_carousel  where product_id="+
					productId+" and images_carousel_id  ="+fileId;
			jpaShareService.executeNativeSql(delSql);
			jpaShareService
					.executeNativeSql(
							"delete from product_images where product_id=? and images_id=?",
							productId, fileId);
		}
		fileService.delFile(fileId);
		result.put(FindOilConstants.SUCCESS, true);
		return result;
	}

	@Override
	public void addToImages(FileInfo fileInfo, Long productId) {
		if (productId != null) {
			jpaShareService
					.executeNativeSql(
							"insert into product_images(product_id,images_id)values(?,?)",
							productId, fileInfo.getId());
		}
	}

	@Override
	public Map<String, Object> update(MultipartFile analysisReportFile,
			Product product, HttpServletRequest request) {
		Map<String, Object> result = StaticMethod.genResult();
		Product productUpdate = productRepository.getOne(product.getId());
		if (productUpdate != null) {
			result.put(FindOilConstants.SUCCESS, true);
			/**
			 * update field
			 */
			StringBuffer hql = new StringBuffer(
					"update Product set lastUpdated=:lastUpdated");
			Map<String, Object> params = new HashMap<String, Object>();
			Calendar now = Calendar.getInstance();
			params.put("lastUpdated", now);
			if (!product.getContent().equals(productUpdate.getContent())) {
				hql.append(",content=:content");
				params.put("content", product.getContent());
			}
			if (!product.getLittleName().equals(productUpdate.getLittleName())) {
				hql.append(",littleName=:littleName");
				params.put("littleName", product.getLittleName());
			}
			if (!product.getMadeFactory()
					.equals(productUpdate.getMadeFactory())) {
				hql.append(",madeFactory=:madeFactory");
				params.put("madeFactory", product.getMadeFactory());
			}
			if (!product.getMarketPrice()
					.equals(productUpdate.getMarketPrice())) {
				hql.append(",marketPrice=:marketPrice");
				params.put("marketPrice", product.getMarketPrice());
			}
			if (!product.getName().equals(productUpdate.getName())) {
				hql.append(",name=:name");
				params.put("name", product.getName());
			}
			if (!product.getProductUnit()
					.equals(productUpdate.getProductUnit())) {
				hql.append(",productUnit=:productUnit");
				params.put("productUnit", product.getProductUnit());
			}
			if (!product.getSellSumNumber().equals(
					productUpdate.getSellSumNumber())) {
				hql.append(",sellSumNumber=:sellSumNumber");
				params.put("sellSumNumber", product.getSellSumNumber());
			}
			jpaShareService.updateForHql(hql.toString(), params);
			/**
			 * update catalog
			 */
			String[] catalogIds = request.getParameterValues("catalogs.id");
			ProductCatalog tempCatalog = null;
			String customCatalogName = "";
			StringBuffer insertSql = new StringBuffer(
					"insert into product_catalogs (product_id,catalogs_id)values ");
			int count = 0;
			for (String id : catalogIds) {
				tempCatalog = productCatalogRepository.getOne(Long
						.parseLong(id));
				if (tempCatalog != null) {
					if (count == 0) {
						insertSql.append("(" + product.getId() + ","
								+ tempCatalog.getId() + ")");
					} else {
						insertSql.append(",(" + product.getId() + ","
								+ tempCatalog.getId() + ")");
					}
					customCatalogName = customCatalogName + " "
							+ tempCatalog.getName();
					count++;
				}
			}
			jpaShareService
					.executeNativeSql("delete from product_catalogs where product_id ="
							+ product.getId());
			jpaShareService.executeNativeSql(insertSql.toString());

			String[] areaIds = request.getParameterValues("area.id");
			Area areaTemp = null;
			insertSql = new StringBuffer(
					"insert into product_areas (product_id,areas_id)values ");
			count = 0;
			for (String id : areaIds) {
				areaTemp = areaRepository.getOne(Long.parseLong(id));
				if (areaTemp != null) {
					if (count == 0) {
						insertSql.append("(" + product.getId() + ","
								+ areaTemp.getId() + ")");
					} else {
						insertSql.append(",(" + product.getId() + ","
								+ areaTemp.getId() + ")");
					}
					count++;
				}
			}
			jpaShareService
					.executeNativeSql("delete from product_areas where product_id ="
							+ product.getId());
			jpaShareService.executeNativeSql(insertSql.toString());

			hql.append(",catalogStr=:catalogStr");
			params.put("catalogStr", customCatalogName);
			jpaShareService.updateForHql(hql.toString(), params);

		}

		return result;
	}

	@Override
	public Map<String, Object> addToCarousel(Long productId, Long fileId) {
		Map<String, Object> result = StaticMethod.genResult();
		Product product = productRepository.getOne(productId);
		if (product != null && checkUser(product)) {
			String insertSql = "insert into  product_images_carousel  (product_id,images_carousel_id )values("+
					productId+","+fileId+")";
			jpaShareService.executeNativeSql(insertSql);
			result.put(FindOilConstants.SUCCESS, true);
		}
		return result;
	}

	@Override
	public Map<String, Object> removeFromCarousel(Long productId, Long fileId) {
		Map<String, Object> result = StaticMethod.genResult();
		Product product = productRepository.getOne(productId);
		if (product != null && checkUser(product)) {
			String delSql = "delete from product_images_carousel    where product_id="+
					productId+" and images_carousel_id  ="+fileId;
			jpaShareService.executeNativeSql(delSql);
			result.put(FindOilConstants.SUCCESS, true);
		}
		return result;
	}

	private boolean checkUser(Product product) {
		boolean result = false;
		User user = userService.currentUser();
		if (product.getUser() == user) {
			result = true;
		}
		return result;

	}

}
