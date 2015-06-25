package com.agilemaster.findoil.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class Article implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2497323226796582634L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@Column
	private String title;
	@Column
	private String keywords;
	@Column(length = 500)
	private String metaDesc;
	@Column(length = 1000)
	private String descirption;
	@Column(columnDefinition = "longtext")
	private String content;
	@ManyToOne()
	private User author;
	@OneToOne
	private FileInfo specImage;
	@Column
	private Boolean online = false;
	@Column
	private Boolean indexShow = false;
	@Column
	private Boolean recommend = false;
	@Column
	private Integer indexSequence = 0;
	@Column
	private Long showTimes;
	/**
	 * 静态指定code,以便在前端固定地方显示
	 */
	@Column(nullable=true)
	private String code;
	@ManyToMany()
	private List<ArticleCatalog> catalogs;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateCreated;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar lastUpdated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getMetaDesc() {
		return metaDesc;
	}

	public void setMetaDesc(String metaDesc) {
		this.metaDesc = metaDesc;
	}

	public String getDescirption() {
		return descirption;
	}

	public void setDescirption(String descirption) {
		this.descirption = descirption;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Calendar getDateCreated() {
		return dateCreated;
	}

	

	public List<ArticleCatalog> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(List<ArticleCatalog> catalogs) {
		this.catalogs = catalogs;
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

	public FileInfo getSpecImage() {
		return specImage;
	}

	public void setSpecImage(FileInfo specImage) {
		this.specImage = specImage;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public Boolean getIndexShow() {
		return indexShow;
	}

	public void setIndexShow(Boolean indexShow) {
		this.indexShow = indexShow;
	}

	public Integer getIndexSequence() {
		return indexSequence;
	}

	public void setIndexSequence(Integer indexSequence) {
		this.indexSequence = indexSequence;
	}

	public Long getShowTimes() {
		return showTimes;
	}

	public void setShowTimes(Long showTimes) {
		this.showTimes = showTimes;
	}

	public Boolean getRecommend() {
		return recommend;
	}

	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}
	
	
}
