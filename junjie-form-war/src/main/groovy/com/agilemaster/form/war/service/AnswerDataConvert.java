package com.agilemaster.form.war.service;

import java.util.Map;

public interface AnswerDataConvert {
	/**
	 * 将回答转化成结构化数据并存储
	 * @param htmlFormId
	 * @param answerId
	 * @param jsonAnswer
	 * @return
	 */
	Map<String,Object> answerForm(String saasId,String htmlFormId,String answerId,String jsonAnswer);
}
