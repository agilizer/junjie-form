package com.agilemaster.findoil.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 
 * @author abel asdtiangxia@163.com 2015年4月17日 下午10:53:55
 */
@Entity
@Table
public class ProductCatalog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7974147296942000277L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@Column
	private String name;
	@Column(length = 1000)
	private String description;
	@Column
	private Boolean lockProduct=false;
	@Column
	private Boolean onlinePay=true;
	
	@ManyToOne()
	private ProductCatalog parentCataLog;
	/**
	 * 目录的排序，值小排在前面
	 */
	@Column(unique = true)
	private int sequence;
	/**
	 * 是否上线
	 */
	@Column
	private Boolean online;
	@OrderBy(value = " sequence ASC ")
	@OneToMany()
	private List<ProductCatalog> childrenCatalogs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductCatalog getParentCataLog() {
		return parentCataLog;
	}

	public void setParentCataLog(ProductCatalog parentCataLog) {
		this.parentCataLog = parentCataLog;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public List<ProductCatalog> getChildrenCatalogs() {
		return childrenCatalogs;
	}

	public void setChildrenCatalogs(List<ProductCatalog> childrenCatalogs) {
		this.childrenCatalogs = childrenCatalogs;
	}

	public Boolean getLockProduct() {
		return lockProduct;
	}

	public void setLockProduct(Boolean lockProduct) {
		this.lockProduct = lockProduct;
	}

	public Boolean getOnlinePay() {
		return onlinePay;
	}

	public void setOnlinePay(Boolean onlinePay) {
		this.onlinePay = onlinePay;
	}
	

}
