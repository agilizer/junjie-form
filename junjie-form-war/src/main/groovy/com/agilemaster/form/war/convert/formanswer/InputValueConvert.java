package com.agilemaster.form.war.convert.formanswer;

import java.util.Date;

import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.alibaba.fastjson.JSONObject;

public interface InputValueConvert {
	/**
	 * 
	 * @param htmlInput
	 * @param answerId
	 * @param inputValueId
	 * @param answerObject
	 * @param dateCreated
	 * @param jsonAnswerCache 更新缓存值
	 * @return
	 */
	InputValue convert(HtmlInput htmlInput,String answerId,String inputValueId,Object answerObject,Date dateCreated,JSONObject jsonAnswerCache);
}
