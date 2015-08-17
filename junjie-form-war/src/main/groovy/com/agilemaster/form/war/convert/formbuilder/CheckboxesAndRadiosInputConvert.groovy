package com.agilemaster.form.war.convert.formbuilder;

import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.HtmlInput.InputType;
import com.alibaba.fastjson.JSONObject;

public class CheckboxesAndRadiosInputConvert extends FormBuilderInputConvertAbstract{
	String OPTIONS="options";
	String LABEL="label";
	String CHECKED="checked";
	String include_other_option="include_other_option";
	public  HtmlInput convertOtherData(HtmlInput htmlInput,InputType inputType,JSONObject jsonObject){
		def selectValues = []
		def rightAnswers=[]
		def optionsArray = jsonObject.getJSONArray(OPTIONS)
		optionsArray.each {
			selectValues.add(it.label)
			if(true==it.checked){
				rightAnswers.add(it.label)
			}
		}
		def otherInfo = [:]
		if(jsonObject.get(include_other_option)){
			otherInfo.put(include_other_option, "true");
			htmlInput.setOtherInfo(otherInfo)
		}
		htmlInput.setSelectValues(selectValues)
		htmlInput.setRightAnswers(rightAnswers)
		return htmlInput
	}
	
}

