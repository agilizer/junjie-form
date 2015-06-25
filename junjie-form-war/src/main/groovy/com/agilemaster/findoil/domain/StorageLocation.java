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
public class StorageLocation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1337683084711425752L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@ManyToOne()
	private Area area;   //所在地区
	@Column
	private String address;  //地址
	@Column
	private String name;   //仓库名
	@Column
	private String contactTel;  //联系电话
	@Column
	private String workTime;   //工作时间
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
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
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
