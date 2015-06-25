package com.agilemaster.findoil.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class ArticleCatalog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8194507406653157169L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@ManyToOne()
	private User author;
	/**
	 * 静态指定code,以便在前端固定地方显示
	 */
	@Column(unique=true)
	private String code;
	@Column(unique=true)
	private String name;
	@Column(unique=true)
	private String seoUrl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSeoUrl() {
		return seoUrl;
	}
	public void setSeoUrl(String seoUrl) {
		this.seoUrl = seoUrl;
	}
	
	

}
