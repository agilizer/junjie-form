package com.agilemaster.findoil.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table
public class WuluRefer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2374712341095806011L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@ManyToOne()
	private User author;
	@Column
	private String productName;
	@Column
	private String packType;
	@Column
	private String weight;
	@Column
	private String startPlace;
	@Column
	private String endPlace;
	@Column
	private String nowPlace;
	@OneToMany()
	private List<Comment> comments;
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar dateCreated;
	@Temporal(TemporalType.TIMESTAMP)
	private    Calendar arriveTime;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPackType() {
		return packType;
	}
	public void setPackType(String packType) {
		this.packType = packType;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getStartPlace() {
		return startPlace;
	}
	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}
	public String getEndPlace() {
		return endPlace;
	}
	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}
	public String getNowPlace() {
		return nowPlace;
	}
	public void setNowPlace(String nowPlace) {
		this.nowPlace = nowPlace;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Calendar getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Calendar dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Calendar getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Calendar arriveTime) {
		this.arriveTime = arriveTime;
	}
	

}
