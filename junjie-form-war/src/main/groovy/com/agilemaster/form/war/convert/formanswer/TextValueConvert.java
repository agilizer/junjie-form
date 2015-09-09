package com.agilemaster.form.war.convert.formanswer;

import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.alibaba.fastjson.JSONObject;

public class TextValueConvert extends FormRenderInputConvertAbstract{

	@Override
	public InputValue convertValue(HtmlInput htmlInput, InputValue inputValue, 
			Object answerObject,JSONObject jsonAnswerCache) {
		inputValue.setStrValue(answerObject.toString());
		jsonAnswerCache.put(VALUE, answerObject);
		return inputValue;
	}

}
