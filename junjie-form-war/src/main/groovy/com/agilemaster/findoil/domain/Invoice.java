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

/**
 * 发票信息
 * @author asdtiang asdtiangxia@163.com
 * 2015年6月3日 上午9:30:30
 */
@Entity
@Table
public class Invoice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7927662731790604263L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@ManyToOne()
	private User user;  //所属用户
	@Column
	private String companyName;   //抬头
	@Column
	private String number;
	@Column
	private String invoiceAddress;
	@Column
	private String invoiceTel;
	@Column
	private String bankName;
	@Column
	private String bankNo;
	/**
	 * 普通发票时使用
	 */
	@Column
	private String content;
	@Column
	private InvoiceType invoiceType;
	
	public static enum InvoiceType{
		NORMAL,
		/**
		 * Value Added Tax (VAT)增值税
		 */
		VAT
	}
	
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getInvoiceAddress() {
		return invoiceAddress;
	}
	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}
	public String getInvoiceTel() {
		return invoiceTel;
	}
	public void setInvoiceTel(String invoiceTel) {
		this.invoiceTel = invoiceTel;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public InvoiceType getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(InvoiceType invoiceType) {
		this.invoiceType = invoiceType;
	}
	

}
