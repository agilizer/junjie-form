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
public class Wulu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2987361793850702789L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@Column
	private String startPlace;
	@Column
	private String toPlace;
	@Column
	private String nowPlace;
	@ManyToOne()
	private WuluType wuluType;
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar dateCreated;
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar startTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStartPlace() {
		return startPlace;
	}
	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}
	public String getToPlace() {
		return toPlace;
	}
	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}
	public String getNowPlace() {
		return nowPlace;
	}
	public void setNowPlace(String nowPlace) {
		this.nowPlace = nowPlace;
	}
	public WuluType getWuluType() {
		return wuluType;
	}
	public void setWuluType(WuluType wuluType) {
		this.wuluType = wuluType;
	}
	public Calendar getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Calendar getStartTime() {
		return startTime;
	}
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	} 

}
