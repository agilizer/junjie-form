package com.agilemaster.findoil.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 产品相关统计信息缓存表
 * @author asdtiang asdtiangxia@163.com
 * 2015年5月23日 上午10:19:19
 */

@Entity
@Table()
public class ProductCache implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -75492754613293576L;
	
	@Id
	@Column
	@GeneratedValue
	private Long id;
	@Column
	private Integer monthOrder=0;
	@Column
	private Integer sumOrder = 0;
	@Column
	private Integer commentCount=0;
	public Integer getMonthOrder() {
		return monthOrder;
	}
	public void setMonthOrder(Integer monthOrder) {
		this.monthOrder = monthOrder;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getSumOrder() {
		return sumOrder;
	}
	public void setSumOrder(Integer sumOrder) {
		this.sumOrder = sumOrder;
	}
	
	
}
