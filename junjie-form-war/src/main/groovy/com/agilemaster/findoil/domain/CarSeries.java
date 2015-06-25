package com.agilemaster.findoil.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class CarSeries implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6098416513997743279L;
	@Id
	@Column
	private String id;
	@Column
 	private String carSeriesId;
	@Column
	private String brandId;
	@Column
	private String brandName;
	@Column
	private String carSeries;
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getCarSeries() {
		return carSeries;
	}
	public void setCarSeries(String carSeries) {
		this.carSeries = carSeries;
	}
	public String getCarSeriesId() {
		return carSeriesId;
	}
	public void setCarSeriesId(String carSeriesId) {
		this.carSeriesId = carSeriesId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
