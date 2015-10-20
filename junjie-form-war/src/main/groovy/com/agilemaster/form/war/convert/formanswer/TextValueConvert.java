package com.agilemaster.form.war.convert.formanswer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.alibaba.fastjson.JSONObject;

public class TextValueConvert extends FormRenderInputConvertAbstract {
	protected Logger log = LoggerFactory
			.getLogger(this.getClass());
	@Override
	public InputValue convertValue(HtmlInput htmlInput, InputValue inputValue,
			Object answerObject, JSONObject jsonAnswerCache) {
		String answerTrim = answerObject.toString().trim();
		inputValue.setStrValue(answerTrim);
		jsonAnswerCache.put(VALUE, answerTrim);
		String rightAnswer = htmlInput.getRightAnswer();
		log.info("aaaaaaaaaaaaaaaaaaaaaaaaa-------------------------------->>>>>>"+htmlInput.getRightAnswer());
		if (null != rightAnswer) {
			log.info("aaaaaaaaaaaaaaaaaaaaaaaaa------"+answerTrim.toString()+rightAnswer.indexOf(answerTrim.toString()) );
			if (rightAnswer.indexOf(answerTrim.toString().trim()) >= 0||rightAnswer.equals("不限")) {
				log.info("bbbbbbbbbbbbbbbbbbbbbbbbb-------------------------------->>>>>>"+htmlInput.getRightAnswer());
				inputValue.setAnswerRight(true);
			}
		}
		return inputValue;
	}
}
