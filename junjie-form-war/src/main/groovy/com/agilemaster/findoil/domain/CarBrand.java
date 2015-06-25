package com.agilemaster.findoil.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class CarBrand implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8073356693593757633L;
	@Id
	@Column
	private String brandId;
	@Column
	private String brandName;
	@Column
	private String brandLetter;
	@Column
	private String imageSrc;
	
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
	public String getImageSrc() {
		return imageSrc;
	}
	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}
	public String getBrandLetter() {
		return brandLetter;
	}
	public void setBrandLetter(String brandLetter) {
		this.brandLetter = brandLetter;
	}
}
