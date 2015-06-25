package com.agilemaster.findoil.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="persistent_logins")
public class PersistentLogin {
	@Id
	@Column
	private String series;
	@Column
	private String name;
	@Column
	private String token;
	@Temporal(TemporalType.TIMESTAMP)     
	private    Calendar lastUsed;   //插入数据库日期
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Calendar getLastUsed() {
		return lastUsed;
	}
	public void setLastUsed(Calendar lastUsed) {
		this.lastUsed = lastUsed;
	}
	
	

}
