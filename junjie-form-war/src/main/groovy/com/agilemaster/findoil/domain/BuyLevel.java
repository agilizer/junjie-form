package com.agilemaster.findoil.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户积分级别元数据 
 * @author asdtiang asdtiangxia@163.com
 * 2015年5月22日 上午7:09:25
 */
@Entity
@Table
public class BuyLevel  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2811628285473691917L;
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@Column
	private Long startSumMoney;
	@Column
	private String name;
	@Column
	private Integer star;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStartSumMoney() {
		return startSumMoney;
	}
	public void setStartSumMoney(Long startSumMoney) {
		this.startSumMoney = startSumMoney;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
	}
	

}
