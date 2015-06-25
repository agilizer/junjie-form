package com.agilemaster.findoil.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * area
 * @author abel asdtiangxia@163.com
 * 2015年4月18日 上午12:09:58
 */
@Entity
@Table
public class Area implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2118630124812985026L;
	@Id
	@Column
	private Long id;
	@ManyToOne()
	private Area parent;
	@Column
	private String name;
	@Column
	private Integer level;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Area getParent() {
		return parent;
	}
	public void setParent(Area parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
}
