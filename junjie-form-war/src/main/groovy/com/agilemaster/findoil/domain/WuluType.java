package com.agilemaster.findoil.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class WuluType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3577865522315263676L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@Column(unique=true)
	private String name;
	@Column(unique=true)
	private String searchUrl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSearchUrl() {
		return searchUrl;
	}
	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}
	

}
