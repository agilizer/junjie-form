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

@Entity
@Table(name = "_order")
public class Order implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6682500656320900511L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	/**
	 * 下订单用户
	 */
	@ManyToOne()
	private User user;
	
	/**
	 * 审核付款凭据用户
	 */
	@ManyToOne()
	private User accessUser;
	@ManyToOne()
	private Product product;
	/**
	 * 下了多少
	 */
	@Column
	private Long orderNumber;  
	/**
	 * 下订单时产品价格（价格会在出售过程中更改）
	 */
	@Column
	private Float orderUnitPrice;
	@Column
	private Float sumPrice;   //总价格,根据orderNumber和product.unitprice 计算得出
	@OneToOne()
	private FileInfo paymentCredential;  //付款凭证
	@Column
	private OrderStatus orderStatus;
	@ManyToOne()
	private OrderAddress orderAddress; 
	
	@Column
	private Boolean canComment= false;
	/**
	 * 发票
	 */
	@ManyToOne()
	private Invoice invoice;
	/**
	 * 审核凭据时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar checkTime;
	/**
	 * 审核凭据通过时间,审核凭据时间时并不一定通过
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar checkAccessTime;  
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar sendTime;     //商品发送日期
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar finishTime;  //交易完成日期
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar lockStartTime; 
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar lockEndTime;
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar paymentStartTime;
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar paymentEndTime;
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar dateCreated;   //数据创建日期
	
	@Column
	private String paymentUuid;
	@OneToOne
	PaymentRecord paymentRecord;
	@OneToOne
	WuluType wuluType;
	@Column
	private String wuluId;
	
	
	public static enum OrderStatus{
		/**
		 * 加入购物车,创建订单后状态
		 */
		CREATE,
		/**
		 * 锁货付款时状态
		 */
		LOCK,
		/**
		 * 锁货时间超时
		 */
		LOCK_FINISH,
		/**
		 * 上传付款凭据后，管理员审核付款
		 */
		CHECK_PAYMENT,
		/**
		 * 在线付款状态
		 */
		PAYMENT,
		PAYMENT_FINISH,
		/**
		 * 付款成功，开始提贷或者物流发贷开始
		 */
		SEND,
		/**
		 * 提贷成功，完成交易
		 */
		FINISH,
		/**
		 * 付款审核失败
		 */
		CHECK_FAIL
	}

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

	public User getAccessUser() {
		return accessUser;
	}

	public void setAccessUser(User accessUser) {
		this.accessUser = accessUser;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Float getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Float sumPrice) {
		this.sumPrice = sumPrice;
	}

	public FileInfo getPaymentCredential() {
		return paymentCredential;
	}

	public void setPaymentCredential(FileInfo paymentCredential) {
		this.paymentCredential = paymentCredential;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public OrderAddress getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(OrderAddress orderAddress) {
		this.orderAddress = orderAddress;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Calendar getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Calendar checkTime) {
		this.checkTime = checkTime;
	}

	public Calendar getCheckAccessTime() {
		return checkAccessTime;
	}

	public void setCheckAccessTime(Calendar checkAccessTime) {
		this.checkAccessTime = checkAccessTime;
	}

	public Calendar getSendTime() {
		return sendTime;
	}

	public void setSendTime(Calendar sendTime) {
		this.sendTime = sendTime;
	}

	public Calendar getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Calendar finishTime) {
		this.finishTime = finishTime;
	}

	public Calendar getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Calendar getLockStartTime() {
		return lockStartTime;
	}

	public void setLockStartTime(Calendar lockStartTime) {
		this.lockStartTime = lockStartTime;
	}

	public Calendar getLockEndTime() {
		return lockEndTime;
	}

	public void setLockEndTime(Calendar lockEndTime) {
		this.lockEndTime = lockEndTime;
	}

	public Float getOrderUnitPrice() {
		return orderUnitPrice;
	}

	public void setOrderUnitPrice(Float orderUnitPrice) {
		this.orderUnitPrice = orderUnitPrice;
	}

	public Boolean getCanComment() {
		return canComment;
	}

	public void setCanComment(Boolean canComment) {
		this.canComment = canComment;
	}

	public Calendar getPaymentStartTime() {
		return paymentStartTime;
	}

	public void setPaymentStartTime(Calendar paymentStartTime) {
		this.paymentStartTime = paymentStartTime;
	}

	public Calendar getPaymentEndTime() {
		return paymentEndTime;
	}

	public void setPaymentEndTime(Calendar paymentEndTime) {
		this.paymentEndTime = paymentEndTime;
	}


	public String getPaymentUuid() {
		return paymentUuid;
	}

	public void setPaymentUuid(String paymentUuid) {
		this.paymentUuid = paymentUuid;
	}

	public PaymentRecord getPaymentRecord() {
		return paymentRecord;
	}

	public void setPaymentRecord(PaymentRecord paymentRecord) {
		this.paymentRecord = paymentRecord;
	}

	public WuluType getWuluType() {
		return wuluType;
	}

	public void setWuluType(WuluType wuluType) {
		this.wuluType = wuluType;
	}

	public String getWuluId() {
		return wuluId;
	}

	public void setWuluId(String wuluId) {
		this.wuluId = wuluId;
	}
	
	

}
