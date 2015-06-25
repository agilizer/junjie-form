package com.agilemaster.findoil.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 在线付款用
 * @author asdtiang asdtiangxia@163.com
 * 2015年5月22日 上午6:46:51
 */
@Entity
@Table()
public class PaymentRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6441170065081038166L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@OneToOne()
	private Order order;
	@Column
	private String paymentIp;
	@Column(columnDefinition = "longtext")
	private String paymentBackStr;
	@Column
	private Boolean salesReturn=false;
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar dateCreated;   //数据创建日期
	@Column
	private PaymentType paymentType;
	
	public static enum PaymentType{
		ALIPAY,
		WEIXIN
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public String getPaymentIp() {
		return paymentIp;
	}
	public void setPaymentIp(String paymentIp) {
		this.paymentIp = paymentIp;
	}
	public String getPaymentBackStr() {
		return paymentBackStr;
	}
	public void setPaymentBackStr(String paymentBackStr) {
		this.paymentBackStr = paymentBackStr;
	}
	public Boolean getSalesReturn() {
		return salesReturn;
	}
	public void setSalesReturn(Boolean salesReturn) {
		this.salesReturn = salesReturn;
	}
	public Calendar getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}
	public PaymentType getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	
}
