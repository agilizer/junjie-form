package com.agilemaster.form.war.convert.formanswer;

import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.alibaba.fastjson.JSONObject;

public class TextValueConvert extends FormRenderInputConvertAbstract {

	@Override
	public InputValue convertValue(HtmlInput htmlInput, InputValue inputValue,
			Object answerObject, JSONObject jsonAnswerCache) {
		String answerTrim = answerObject.toString().trim();
		inputValue.setStrValue(answerTrim);
		jsonAnswerCache.put(VALUE, answerTrim);
		String rightAnswer = htmlInput.getRightAnswer();
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa-------------------------------->>>>>>"+htmlInput.getRightAnswer());
		if (null != rightAnswer) {
			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa------"+answerTrim.toString()+rightAnswer.indexOf(answerTrim.toString()) );
			if (rightAnswer.indexOf(answerTrim.toString().trim()) >= 0) {
				System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbb-------------------------------->>>>>>"+htmlInput.getRightAnswer());
				inputValue.setAnswerRight(true);
			}
		}
		return inputValue;
	}
}
