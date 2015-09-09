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
		def removeIndex = -1
		optionsArray.eachWithIndex { item,index->
			selectValues.add(item.label)
			if(true==item.checked){
				rightAnswers.add(item.label)
			}
			if(item.label=='不限'){
				removeIndex = index
			}
		}
		
		def otherInfo = [:]
		if(jsonObject.get(include_other_option)){
			otherInfo.put(include_other_option, "true");
			htmlInput.setOtherInfo(otherInfo)
		}
		htmlInput.setSelectValues(selectValues)
		htmlInput.setRightAnswers(rightAnswers)
		if(removeIndex>0){
			optionsArray.remove(removeIndex)
		}
		return htmlInput
	}
	
}

