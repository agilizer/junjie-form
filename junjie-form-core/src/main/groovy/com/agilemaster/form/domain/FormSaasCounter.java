package com.agilemaster.form.domain;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * 记录每个saas用户有多少个form;
 * @author asdtiang
 *
 */
@Table(keyspace = "junjie_form",name = "FormSaasCounter")
public class FormSaasCounter{
	@PartitionKey
	private String  id;
	private long counterValue;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getCounterValue() {
		return counterValue;
	}
	public void setCounterValue(long counterValue) {
		this.counterValue = counterValue;
	}
}
