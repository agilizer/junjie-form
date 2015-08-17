package com.agilemaster.form.war.convert.formanswer;

import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.alibaba.fastjson.JSONObject;

public class NumberValueConvert extends FormRenderInputConvertAbstract{

	@Override
	public InputValue convertValue(HtmlInput htmlInput, InputValue inputValue, Object answerObject,JSONObject jsonAnswerCache) {
		inputValue.setStrValue(answerObject.toString());
		Long number = 0l ;
		try{
			number = Long.parseLong(answerObject.toString());
		}catch(Exception e){
			log.warn("inputValue number convert warn, input is not a number.use default value 0");
		}
		inputValue.setNumberValue(number);
		jsonAnswerCache.put(VALUE, answerObject);
		return inputValue;
	}

}
