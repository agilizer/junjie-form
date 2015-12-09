package com.agilemaster.form.domain;

import com.agilemaster.form.constants.JunjieFormConstants;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * saas counter,有多少个saas用户，即FormSaas的数据条数。
 * 暂时没有使用select count(*) from FormSaas实现（这样实现数据量大时返回有限制）。
 * @author asdtiang
 *
 */
@Table(keyspace =JunjieFormConstants.DEFAULT_KEY_SPACE,name = "SaasCounter")
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