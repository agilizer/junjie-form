package com.agilemaster.form.war.convert.formbuilder

import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.HtmlInput.InputType;
import com.alibaba.fastjson.JSONObject;


class SectionBreakConvert extends FormBuilderInputConvertAbstract{
	
	def INFO_ATTR_NAMES=["description","min_max_length_units"]
	public  HtmlInput convertOtherData(HtmlInput htmlInput,InputType inputType,JSONObject jsonObject){
		def otherInfo = [:]
		def inputAttrs=[:]
		def tempValue = null
		INFO_ATTR_NAMES.each {
			tempValue = jsonObject.getString(it)
			if(tempValue){
				otherInfo.put(it, tempValue)
			}
		}
		if(otherInfo.size()>0){
			htmlInput.setOtherInfo(otherInfo);
		}
		if(inputAttrs.size()>0){
			htmlInput.setInputAttrs(inputAttrs)
		}
		return htmlInput
	}
	
}
