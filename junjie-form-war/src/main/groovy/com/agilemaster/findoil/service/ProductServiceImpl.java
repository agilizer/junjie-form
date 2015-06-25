package com.agilemaster.findoil.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
import com.agilemaster.findoil.domain.Product.ProductStatus;
import com.agilemaster.findoil.domain.ProductCache;
import com.agilemaster.findoil.domain.ProductCatalog;
import com.agilemaster.findoil.domain.StorageLocation;
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
import com.agilemaster.share.service.JdbcPage;

@Service
public class ProductServiceImpl implements ProductService {
	private static final Logger log = LoggerFactory
			.getLogger(ProductServiceImpl.class);
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
	public JdbcPage list(int max, int offset, ProductStatus productStatus) {
		String hql = "FROM Product p WHERE p.productStatus=:productStatus ";
		String countHql = "SELECT COUNT(*) FROM Product  p WHERE p.productStatus=:productStatus";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("productStatus", productStatus);
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, countHql, max,
				offset, parameterMap);
		return jdbcPage;
	}

	@Override
	public JdbcPage resourceList(int max, int offset,
			Map<String, Object> parameterMap) {
		String madeFactory = (String) parameterMap
				.get(ProductService.QUERY__KEY_MADE_FACTORY);
		parameterMap.clear();
		String hql = "FROM Product p  where 1=1  ";
		String countHql = "SELECT COUNT(*) FROM Product p  where 1=1 ";
		if (madeFactory != null && !"".equals(madeFactory.trim())) {
			hql += " AND p.madeFactory like:madeFactory";
			countHql += " AND p.madeFactory like:madeFactory ";
			parameterMap.put("madeFactory", "%" + madeFactory + "%");
		}
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, countHql, max,
				offset, parameterMap);
		return jdbcPage;
	}

	@Override
	public JdbcPage resourceList(Map<String, Object> parameterMap) {
		String toPageNum = (String) parameterMap.get("toPageNum");
		String storageLocationId = (String) parameterMap
				.get("storageLocationId");
		String madeFactory = (String) parameterMap.get("madeFactory");
		parameterMap.clear();
		String hql = "select distinct p FROM Product p  join p.catalogs catalog";
		String countHql = "SELECT COUNT(*) FROM Product p ";
		if (storageLocationId != null && !"".equals(storageLocationId.trim())) {
			hql += " AND p.storageLocation.id=:storageLocationId";
			countHql += " AND p.storageLocation.id=:storageLocationId";
			parameterMap.put("storageLocationId",
					Long.parseLong(storageLocationId));
		}
		if (madeFactory != null && !"".equals(madeFactory.trim())) {
			hql += " AND p.madeFactory like :madeFactory";
			countHql += " AND p.madeFactory like :madeFactory";
			parameterMap.put("madeFactory", madeFactory + "%");
		}
		int offset = 1;
		if (toPageNum != null && !"".equals(toPageNum)) {
			offset = (Integer.parseInt(toPageNum) - 1) * 10;
		} else {
			offset = 0;
		}
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, countHql, 10,
				offset, null);
		return jdbcPage;
	}

	@Override
	public Boolean save(MultipartFile analysisReportFile,
			MultipartFile mainPhoto, Product product, HttpServletRequest request) {
		FileInfo analysisFileInfo = null;
		if (!analysisReportFile.isEmpty()) {
			try {
				analysisFileInfo = saveFileInfo(analysisReportFile);
			} catch (Exception e) {
				log.error("save analysisFileInfo error", e);
				return false;
			}
		}
		product.setAnalysisReport(analysisFileInfo);

		String[] imageIds = request.getParameterValues("images.id");
		List<FileInfo> fileInfoes = new ArrayList<FileInfo>();
		FileInfo tempFileInfo = null;
		for (String id : imageIds) {
			tempFileInfo = fileInfoRepository.getOne(Long.parseLong(id));
			if (tempFileInfo != null) {
				fileInfoes.add(tempFileInfo);
			}
		}
		product.setImages(fileInfoes);
		
		String[] imagesCarouselIds = request.getParameterValues("imagesCarousel.id");
		if(null!=imagesCarouselIds){
			fileInfoes.clear();;
			tempFileInfo = null;
			for (String id : imagesCarouselIds) {
				tempFileInfo = fileInfoRepository.getOne(Long.parseLong(id));
				if (tempFileInfo != null) {
					fileInfoes.add(tempFileInfo);
				}
			}
			product.setImagesCarousel(fileInfoes);
		}
		// 销售总吨数
		// 每吨价格
		User user = userService.currentUser();
		// customCatalogName

		String customCatalogName = "";
		String[] catalogIds = request.getParameterValues("catalogs.id");
		List<ProductCatalog> catalogs = new ArrayList<ProductCatalog>();
		ProductCatalog tempCatalog = null;
		for (String id : catalogIds) {
			tempCatalog = productCatalogRepository.getOne(Long.parseLong(id));
			if (tempCatalog != null) {
				catalogs.add(tempCatalog);
				customCatalogName = customCatalogName + " "
						+ tempCatalog.getName();
			}
		}
		product.setCatalogStr(request.getParameter("catalogJoinName")
				+ customCatalogName);
		product.setCatalogs(catalogs);

		String[] locationIds = request.getParameterValues("storageLocation.id");
		List<StorageLocation> locations = new ArrayList<StorageLocation>();
		StorageLocation tempLocation = null;
		if(locationIds!=null){
			for (String id : locationIds) {
				tempLocation = storageLocationRepository.getOne(Long.parseLong(id));
				if (tempLocation != null) {
					locations.add(tempLocation);
				}
			}
			product.setStorageLocations(locations);
		}
		
		
		String[] areaIds = request.getParameterValues("area.id");
		List<Area> areas = new ArrayList<Area>();
		Area tempArea = null;
		if(areaIds!=null){
			for (String id : areaIds) {
				tempArea = areaRepository.getOne(Long.parseLong(id));
				if (tempArea != null) {
					areas.add(tempArea);
				}
			}
			product.setAreas(areas);
		}
		Calendar now = Calendar.getInstance();
		product.setProductStatus(ProductStatus.CHECK);
		product.setDateCreated(now);
		product.setLastUpdated(now);
		product.setSubmitCheckTime(now);
		// 记录提交人
		product.setUser(user);
		ProductCache cache = new ProductCache();
		jpaShareService.save(cache);
		product.setProductCache(cache);
		productRepository.save(product);
		return true;
	}

	private FileInfo saveFileInfo(MultipartFile file)
			throws IllegalStateException, IOException {
		String path = "/test";
		FileInfo fileInfo = new FileInfo();
		String filePath = rootPath
				+ (path.startsWith("/") ? path : ("/" + path));
		String fileName = file.getOriginalFilename();
		fileInfo.setOriginalName(fileName);
		fileInfo.setFileSize(file.getSize());
		// unique the file name
		int dotPos = fileName.lastIndexOf(".");
		// 如下就是获取扩展名
		String extension = (dotPos == -1 && (dotPos + 1) >= fileName.length()) ? ""
				: fileName.substring(dotPos + 1, fileName.length());
		// 这里就修改为新名字
		fileName = System.currentTimeMillis() + "." + extension;
		// String downloadPath = "/upload/test/"+fileName;
		// fileInfo.setDownloadPath(downloadPath);
		File newFile = new File(filePath, fileName);
		// 这里应该设置保存新地址
		fileInfo.setStorePath(filePath + "/" + fileName);
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		file.transferTo(newFile);
		fileInfo.setDateCreated(Calendar.getInstance());
		fileInfo = fileInfoRepository.save(fileInfo);
		return fileInfo;
	}

	@Override
	public Product getDetail(Long id) {
		return productRepository.findOne(id);
	}

	@Override
	public Boolean check(Long id, Boolean result, User user) {
		Product product = productRepository.findOne(id);
		Boolean returnResult = false;
		if (product != null) {
			// 将审核人保存入信息
			product.setAccessUser(user);
			// 审核日期
			product.setSubmitCheckTime(Calendar.getInstance());
			if (result) {
				// 审核通过日期
				product.setProductStatus(ProductStatus.ONLINE);
				product.setAccessTime(Calendar.getInstance());
			} else {
				product.setProductStatus(ProductStatus.CHECK_FAIL);
			}
			productRepository.saveAndFlush(product);
			returnResult = true;
		}
		return returnResult;
	}

	public JdbcPage list(int max, int offset, Map<String, Object> parameterMap,
			User user) {
		ProductStatus productStatus = (ProductStatus) parameterMap
				.get("productStatus");
		String storageLocationId = (String) parameterMap
				.get("storageLocationId");
		String madeFactory = (String) parameterMap.get("madeFactory");
		String productCatalogId = (String) parameterMap.get("productCatalogId");
		parameterMap.clear();
		String hql = "FROM Product p WHERE p.productStatus=:productStatus AND p.user.id =:userId";
		String countHql = "SELECT COUNT(*) FROM Product  p WHERE p.productStatus=:productStatus AND p.user.id =:userId";
		parameterMap.put("productStatus", productStatus);
		parameterMap.put("userId", user.getId());
		if (storageLocationId != null && !"".equals(storageLocationId.trim())) {
			hql += " AND p.storageLocation.id=:storageLocationId";
			countHql += " AND p.storageLocation.id=:storageLocationId";
			parameterMap.put("storageLocationId",
					Long.parseLong(storageLocationId));
		}
		if (madeFactory != null && !"".equals(madeFactory.trim())) {
			hql += " AND p.madeFactory like :madeFactory";
			countHql += " AND p.madeFactory like :madeFactory";
			parameterMap.put("madeFactory", madeFactory + "%");
		}
		if (productCatalogId != null && !"".equals(productCatalogId.trim())) {
			hql += " AND p.catalog.id=:productCatalogId";
			countHql += " AND p.catalog.id= 	:productCatalogId";
			parameterMap.put("productCatalogId",
					Long.parseLong(productCatalogId));
		}
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, countHql, max,
				offset, parameterMap);
		return jdbcPage;
	}

	@Override
	public JdbcPage list(int max, int offset, Map<String, Object> parameterMap) {
		Map<String, Object> queryParams = new HashMap<String,Object>();
		ProductStatus productStatus = (ProductStatus) parameterMap
				.get("productStatus");
		String madeFactory = (String) parameterMap.get("madeFactory");
		StringBuffer hql = new StringBuffer("select distinct p FROM Product  p join p.catalogs catalog join p.areas area   WHERE p.productStatus=:productStatus ");
		StringBuffer countHql = new StringBuffer("SELECT COUNT(distinct p.id) FROM Product  p join p.catalogs catalog join p.areas area  WHERE p.productStatus=:productStatus");
		queryParams.put("productStatus", productStatus);
		if (madeFactory != null && !"".equals(madeFactory.trim())) {
			hql.append(" AND p.madeFactory like :madeFactory");
			countHql.append(" AND p.madeFactory like :madeFactory");
			queryParams.put("madeFactory", madeFactory + "%");
		}
		Long[] productCatalogIds = (Long[]) parameterMap
				.get("productCatalogIds");
		Long[] areaIds = (Long[]) parameterMap
				.get("areaIds");
		genOrHql(hql,countHql,"catalog",productCatalogIds);
		genOrHql(hql,countHql,"area",areaIds);
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql.toString(), countHql.toString(), max,
				offset, queryParams);
		return jdbcPage;
	}
	
	private void genOrHql(StringBuffer hql,StringBuffer countHql,String tableName,Long[] ids){
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				if (i == 0) {
					hql.append(" AND ("+tableName+".id=" + ids[i] + " ");
					countHql.append(" AND ("+tableName+".id=" + ids[i]
							+ " ");
				} else {
					hql.append(" or "+tableName+".id=" + ids[i] + " ");
					countHql.append(" or "+tableName+".id=" + ids[i] + " ");
				}
			}
			hql.append(" ) ");
			countHql.append( " ) ");
		}
	}

	@Override
	public JdbcPage listByUser(int max, int offset) {
		String hql = "FROM Product p WHERE p.user.id=:userId order by p.id desc";
		String countHql = "SELECT COUNT(*) FROM Product  p WHERE p.user.id=:userId";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("userId", userService.currentUser().getId());
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, countHql, max,
				offset, parameterMap);
		return jdbcPage;
	}

	

}
