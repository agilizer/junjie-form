package com.agilemaster.form.domain;

import com.agilemaster.form.constants.JunjieFormConstants;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
/**
 * 记录每个表单有多少个用户提交了答案
 * @author asdtiang
 *
 */
@Table(keyspace = JunjieFormConstants.DEFAULT_KEY_SPACE, name = "HtmlFormCounter")
public class HtmlFormCounter{
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
