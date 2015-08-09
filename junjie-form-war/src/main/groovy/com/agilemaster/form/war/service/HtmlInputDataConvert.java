package com.agilemaster.form.war.service;

import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.HtmlInput.InputType;
import com.alibaba.fastjson.JSONObject;

public interface HtmlInputDataConvert {

	/**
	 * 
	 * @param inputType
	 * @param jsonObject htmlInput json 
	 * @return
	 */
	HtmlInput convert(InputType inputType,JSONObject jsonObject );
}
