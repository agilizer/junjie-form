package com.agilemaster.findoil.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class CarModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private String id;
	@Column
	private String seriesId;
	@Column
	private String exhaustVolume;
	@Column
	private String years;
	@Column
	private String transmission;
	@Column
	private Boolean requestJuhe = false;
	/**
	 * 粘度等级
	 */
	@Column
	private String sae;
	/**
	 * 油品质量等级
	 */
	@Column
	private String api;
//	 "exhaustVolume": "2.0",
//     "id": "103895",
//     "transmission": "手动变速器(MT)",
//     "years": "2010"
	
	public String getSeriesId() {
		return seriesId;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSae() {
		return sae;
	}
	public void setSae(String sae) {
		this.sae = sae;
	}
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public void setSeriesId(String seriesId) {
		this.seriesId = seriesId;
	}
	public String getExhaustVolume() {
		return exhaustVolume;
	}
	public void setExhaustVolume(String exhaustVolume) {
		this.exhaustVolume = exhaustVolume;
	}
	public String getYears() {
		return years;
	}
	public void setYears(String years) {
		this.years = years;
	}
	public String getTransmission() {
		return transmission;
	}
	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public Boolean getRequestJuhe() {
		return requestJuhe;
	}

	public void setRequestJuhe(Boolean requestJuhe) {
		this.requestJuhe = requestJuhe;
	}
	
}
