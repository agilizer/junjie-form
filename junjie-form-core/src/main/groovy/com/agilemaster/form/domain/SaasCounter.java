package com.agilemaster.form.domain;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * saas counter
 * @author asdtiang
 *
 */
@Table(keyspace = "junjie_form",name = "SaasCounter")
public class SaasCounter{
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