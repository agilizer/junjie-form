package com.agilemaster.findoil.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table()
public class ProductPriceChange  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5736509973234149120L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Product product;
	@Column
	private Float sourcePrice;
	@Column
	private Float changePrice;
	@Column
	private Float targetPrice;
	@ManyToOne
	private User author;
	@ManyToOne
	private User accessUser;
	@Column
	private ChangeStatus changeStatus;
	@Temporal(TemporalType.TIMESTAMP)  
	private Calendar submitDate;
	@Temporal(TemporalType.TIMESTAMP)  
	private Calendar accessDate;
	
	
	public static enum ChangeStatus{
		SUBMIT,
		ACCESS,
		FAIL
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public Float getSourcePrice() {
		return sourcePrice;
	}


	public void setSourcePrice(Float sourcePrice) {
		this.sourcePrice = sourcePrice;
	}


	public Float getChangePrice() {
		return changePrice;
	}


	public void setChangePrice(Float changePrice) {
		this.changePrice = changePrice;
	}


	public Float getTargetPrice() {
		return targetPrice;
	}


	public void setTargetPrice(Float targetPrice) {
		this.targetPrice = targetPrice;
	}


	public User getAuthor() {
		return author;
	}


	public void setAuthor(User author) {
		this.author = author;
	}


	public User getAccessUser() {
		return accessUser;
	}


	public void setAccessUser(User accessUser) {
		this.accessUser = accessUser;
	}


	

	public ChangeStatus getChangeStatus() {
		return changeStatus;
	}


	public void setChangeStatus(ChangeStatus changeStatus) {
		this.changeStatus = changeStatus;
	}


	public Calendar getSubmitDate() {
		return submitDate;
	}


	public void setSubmitDate(Calendar submitDate) {
		this.submitDate = submitDate;
	}


	public Calendar getAccessDate() {
		return accessDate;
	}


	public void setAccessDate(Calendar accessDate) {
		this.accessDate = accessDate;
	}
	
	

}
