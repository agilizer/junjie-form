package com.agilemaster.form.domain;

import java.util.List;
import java.util.UUID;

/**
 * 租户，一个租户有多个表单。
 * @author asdtiang
 *
 */
public class FormSaas {
	UUID saasId;
	private List<HtmlForm> forms;
	public UUID getSaasId() {
		return saasId;
	}
	public void setSaasId(UUID saasId) {
		this.saasId = saasId;
	}
	
	
	
}
