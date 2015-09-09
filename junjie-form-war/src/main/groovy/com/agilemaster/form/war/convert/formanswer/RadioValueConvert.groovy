package com.agilemaster.form.war.convert.formanswer

import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.InputValue;
import com.alibaba.fastjson.JSONObject;

class RadioValueConvert extends FormRenderInputConvertAbstract{

	
	@Override
	public InputValue convertValue(HtmlInput htmlInput, InputValue inputValue, 
		Object answerObject,JSONObject jsonAnswerField) {
		answerObject = answerObject.toString().trim()
		inputValue.setStrValue(answerObject.toString());
		List<String> rightValues = htmlInput.getRightAnswers();
		List<String> answerValues = new ArrayList<String>();
		println jsonAnswerField.toJSONString()
		log.info("answerObject-->"+answerObject.getClass().getName());
		def selectItems = jsonAnswerField.getJSONObject("field_options").getJSONArray("options")
		def valueMap = [:]
		println "llllllllllllllllllllllllllllll"
		selectItems.eachWithIndex {value,index->
			println "------>"+value.label+":"+answerObject.toString()
			if(value.label==answerObject.toString()){
				value.checked=true
				println"---------------------->true"
			}
		}
		def rightValue = htmlInput.getRightAnswer()
		if(rightValue){
			if(RIGHT_VALUE_ALL==rightValue){
				inputValue.setAnswerRight(true)
			}
			else{
				if(rightValue.indexOf(answerObject)>=0){
					inputValue.setAnswerRight(true)
				}
			}
		}		
		inputValue.setListValue(answerValues);
		return inputValue;
	}
}
