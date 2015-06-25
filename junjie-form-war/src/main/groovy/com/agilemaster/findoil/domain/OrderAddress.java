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
@Table
public class OrderAddress implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1817585524719663475L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@Column
	private String address;  //邮寄地址
	@Column
	private String tel;		//联系方式
	@Column
	private String name;		//联系人
	@ManyToOne()
	private User user;		//所属用户
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}
