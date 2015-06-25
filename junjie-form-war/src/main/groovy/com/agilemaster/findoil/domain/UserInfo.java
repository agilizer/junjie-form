package com.agilemaster.findoil.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table
public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8520316315173417991L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@Column
	private String email;
	@Column
	private String qq;
	@Column
	private String trueName;
	/**
	 * 用于记录用户购买润滑油分类，以便下次登录直接筛选和推荐
	 */
	@ManyToMany
	private List<ProductCatalog> myCatalogs;
	
	@Column
	private String companyName;
	@Column
	private String companyTel;
	@Column
	private String companyFax;
	@Column
	private String companyAddress;
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar dateCreated;  
	/**
	 * 最后更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar lastUpdated;  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyTel() {
		return companyTel;
	}
	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}
	public String getCompanyFax() {
		return companyFax;
	}
	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public Calendar getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Calendar getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public List<ProductCatalog> getMyCatalogs() {
		return myCatalogs;
	}
	public void setMyCatalogs(List<ProductCatalog> myCatalogs) {
		this.myCatalogs = myCatalogs;
	}
	
	
}
