package com.agilemaster.form.war.convert.formanswer

import com.agilemaster.form.domain.HtmlInput
import com.agilemaster.form.domain.InputValue
import com.alibaba.fastjson.JSONObject;

class CheckListValueConvert extends FormRenderInputConvertAbstract{

	@Override
	public InputValue convertValue(HtmlInput htmlInput, InputValue inputValue, Object answerObject,JSONObject jsonAnswerCache) {
		inputValue.setStrValue(answerObject.toString());
		List<String> selectValues = htmlInput.getSelectValues();
		List<String> rightValues = htmlInput.getRightAnswers();
		boolean checkRight =  null==rightValues?false:true
		List<String> answerValues = new ArrayList<String>();
		def answerStr = ""
		log.info("answerObject-->"+answerObject.getClass().getName());
		def valueMap = [:]
		selectValues.eachWithIndex {value,index->
			if("on" == answerObject.get(index+"")){
				answerValues.add(value);
				answerStr = answerStr +"  "+value;
				if(checkRight){
					rightValues.remove(value);
				}
				valueMap.put(index+"", "on");
			}else{
				valueMap.put(index+"", "false");
			}
		}
		if(checkRight){
			if(rightValues.empty){
				inputValue.setAnswerRight(true);
			}
		}
		inputValue.setListValue(answerValues);
		inputValue.setStrValue(answerStr);
		jsonAnswerCache.put(VALUE, answerObject);
		return inputValue;
	}
}
