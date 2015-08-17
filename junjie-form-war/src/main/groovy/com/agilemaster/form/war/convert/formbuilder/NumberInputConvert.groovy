package com.agilemaster.form.war.convert.formbuilder

import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.HtmlInput.InputType;
import com.alibaba.fastjson.JSONObject;

/**
 * "field_options":{"units":"unit","min":"23","max":"45","integer_only":true}
 * @author asdtiang
 *
 */
class NumberInputConvert  extends FormBuilderInputConvertAbstract{
	
	def INFO_ATTR_NAMES=["units","integer_only"]
	def INPUT_ATTR_NAMES = ["min","max"]
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
		INPUT_ATTR_NAMES.each {
			tempValue = jsonObject.getString(it)
			if(tempValue){
				inputAttrs.put(it,tempValue)
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
