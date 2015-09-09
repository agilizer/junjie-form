package com.agilemaster.form.war.convert.formanswer;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.alibaba.fastjson.JSONObject;

public abstract class FormRenderInputConvertAbstract implements InputValueConvert{

	protected Logger log = LoggerFactory
			.getLogger(this.getClass());
	public static String VALUE="value";
	@Override
	public InputValue convert(HtmlInput htmlInput, String answerId,String inputValueId,
			Object answerObject,Date dateCreated,JSONObject jsonAnswerField) {
		InputValue inputValue = new InputValue();
		inputValue.setId(inputValueId);
		inputValue.setAnswerId(answerId);
		Date now = new Date();
		if(null == dateCreated){
			inputValue.setDateCreated(now);
		}
		inputValue.setLastUpdated(now);
		inputValue.setLabel(htmlInput.getLabelBefore());
		inputValue.setFormId(htmlInput.getFormId());
		inputValue.setHtmlInputId(htmlInput.getId());
		convertValue(htmlInput,inputValue,answerObject,jsonAnswerField);
		return inputValue;
	}
	/**
	 * 
	 * @param htmlInput
	 * @param inputValue
	 * @param answerObject
	 * @param jsonAnswerCache 一个input元素jsonObject
	 * @return
	 */
	public abstract InputValue convertValue(HtmlInput htmlInput, InputValue inputValue,
			Object answerObject,JSONObject jsonAnswerField);

}
