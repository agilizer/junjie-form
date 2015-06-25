package com.agilemaster.findoil.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table()
public class Product implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7510799971055224768L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@Column
	private String name;
	@Column
	private String littleName;
	/**
	 * 产品类别
	 */
	@ManyToMany()
	private List<ProductCatalog> catalogs; // 产品级别
	@ManyToMany()
	private List<CarBrand> carBrands; // 产品级别
	
	@ManyToMany()
	private List<CarSeries> carSeries; // 产品级别
	
	@Column
	private String madeFactory;   //制造工厂
	@Column
	private String qualityGrade;   
	@Column
	private String viscosityGrade;   
	/**
	 * 总共卖多少吨，库存
	 */
	@Column
	private Long sellSumNumber;     
	/**
	 * 已经卖出多少吨
	 */
	@Column
	private Long selledNumber  = 0L;    
	@Column
	private ProductStatus productStatus;  //产品状态
	/**
	 * 单价,多少钱/吨
	 */
	@Column
	private Float unitPrice; 
	/**
	 * 市场价
	 */
	@Column
	private Float marketPrice;
	@Column
	private String productUnit;
	@Column
	private String catalogStr;
	/**
	 * 最后一次价格波动（正负都可以）
	 */
	@Column
	private Float latestChangePrice;
	/**
	 * 提交资源单用户
	 */
	@ManyToOne
	private User user;
	/**
	 * 审核通过用户
	 */
	@ManyToOne
	private User accessUser;
	@ManyToOne
	private ProductPriceChange currentPriceChange;
	
	@OneToOne()
	private FileInfo analysisReport;   //分析报告
	
	@OneToOne()
	private FileInfo mainPhoto;
	
	@OneToOne()
	private ProductCache productCache;
	
	@OneToMany()
	private List<FileInfo> images;
	
	@OneToMany()
	private List<FileInfo> imagesCarousel;
	
	
	@Column(columnDefinition = "longtext")
	private String content;
	@ManyToMany()
	private List<StorageLocation> storageLocations; //仓库信息
	@Temporal(TemporalType.TIMESTAMP)     
	private    Calendar dateCreated;   //插入数据库日期
	@Temporal(TemporalType.TIMESTAMP)     
	private    Calendar lastUpdated;
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar submitCheckTime;   //审核日期
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar accessTime;   //审核通过日期,与审核日期不冲突
	/**
	 * 卖完时的日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar finishTime;  
	
	
	@OneToMany()
	private List<Comment> comments;
	
	@ManyToMany()
	private List<Area> areas;
	
	public static enum ProductStatus{
		/**
		 * 草稿,用户后台需要显示
		 */
		DRAFT,
		/**
		 * 提交审核,这时用户不能更改
		 */
		CHECK,
		/**
		 * 审核失败
		 */
		CHECK_FAIL,
		/**
		 * 审核成功,上线开始出售
		 */
		ONLINE,
		/**
		 * 卖完
		 */
		FINISH		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public List<ProductCatalog> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(List<ProductCatalog> catalogs) {
		this.catalogs = catalogs;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getMadeFactory() {
		return madeFactory;
	}

	public void setMadeFactory(String madeFactory) {
		this.madeFactory = madeFactory;
	}

	public String getQualityGrade() {
		return qualityGrade;
	}

	public void setQualityGrade(String qualityGrade) {
		this.qualityGrade = qualityGrade;
	}

	public String getViscosityGrade() {
		return viscosityGrade;
	}

	public void setViscosityGrade(String viscosityGrade) {
		this.viscosityGrade = viscosityGrade;
	}


	public Long getSellSumNumber() {
		return sellSumNumber;
	}

	public void setSellSumNumber(Long sellSumNumber) {
		this.sellSumNumber = sellSumNumber;
	}

	public Long getSelledNumber() {
		return selledNumber;
	}

	public void setSelledNumber(Long selledNumber) {
		this.selledNumber = selledNumber;
	}

	public ProductStatus getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(ProductStatus productStatus) {
		this.productStatus = productStatus;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public FileInfo getAnalysisReport() {
		return analysisReport;
	}

	public void setAnalysisReport(FileInfo analysisReport) {
		this.analysisReport = analysisReport;
	}

	

	public List<StorageLocation> getStorageLocations() {
		return storageLocations;
	}

	public void setStorageLocations(List<StorageLocation> storageLocations) {
		this.storageLocations = storageLocations;
	}

	public Calendar getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Calendar getSubmitCheckTime() {
		return submitCheckTime;
	}

	public void setSubmitCheckTime(Calendar submitCheckTime) {
		this.submitCheckTime = submitCheckTime;
	}

	public Calendar getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Calendar accessTime) {
		this.accessTime = accessTime;
	}

	public Calendar getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Calendar finishTime) {
		this.finishTime = finishTime;
	}

	public User getAccessUser() {
		return accessUser;
	}

	public void setAccessUser(User accessUser) {
		this.accessUser = accessUser;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public String getCatalogStr() {
		return catalogStr;
	}

	public void setCatalogStr(String catalogStr) {
		this.catalogStr = catalogStr;
	}


	public ProductPriceChange getCurrentPriceChange() {
		return currentPriceChange;
	}

	public void setCurrentPriceChange(ProductPriceChange currentPriceChange) {
		this.currentPriceChange = currentPriceChange;
	}

	public Float getLatestChangePrice() {
		return latestChangePrice;
	}

	public void setLatestChangePrice(Float latestChangePrice) {
		this.latestChangePrice = latestChangePrice;
	}

	public FileInfo getMainPhoto() {
		return mainPhoto;
	}

	public void setMainPhoto(FileInfo mainPhoto) {
		this.mainPhoto = mainPhoto;
	}

	public List<FileInfo> getImages() {
		return images;
	}

	public void setImages(List<FileInfo> images) {
		this.images = images;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLittleName() {
		return littleName;
	}

	public void setLittleName(String littleName) {
		this.littleName = littleName;
	}

	public ProductCache getProductCache() {
		return productCache;
	}

	public void setProductCache(ProductCache productCache) {
		this.productCache = productCache;
	}

	public Float getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Float marketPrice) {
		this.marketPrice = marketPrice;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public Calendar getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public List<FileInfo> getImagesCarousel() {
		return imagesCarousel;
	}

	public void setImagesCarousel(List<FileInfo> imagesCarousel) {
		this.imagesCarousel = imagesCarousel;
	}

	public List<CarBrand> getCarBrands() {
		return carBrands;
	}

	public void setCarBrands(List<CarBrand> carBrands) {
		this.carBrands = carBrands;
	}

	public List<CarSeries> getCarSeries() {
		return carSeries;
	}

	public void setCarSeries(List<CarSeries> carSeries) {
		this.carSeries = carSeries;
	}
	
	
}

