package com.agilemaster.form.war.service;

import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.domain.HtmlInput.InputType;

public interface HtmlFormDataConvert {
	HtmlForm convert(String htmlFormId,String jsonContent);
	void registerHtmlInputConvert(InputType inputType,HtmlInputDataConvert inputDataConvert);
}
