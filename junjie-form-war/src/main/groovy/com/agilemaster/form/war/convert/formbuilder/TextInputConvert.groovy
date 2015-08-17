package com.agilemaster.form.war.convert.formbuilder;

import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.HtmlInput.InputType;
import com.alibaba.fastjson.JSONObject;

/**
 *  "size": "small",
        "description": "adsfasfasdfadsfasdfsadf",
        "minlength": "20",
        "maxlength": "30",
        "min_max_length_units": "characters"
 * @author asdtiang
 *
 */
public class TextInputConvert extends FormBuilderInputConvertAbstract{
	
	def INFO_ATTR_NAMES=["size","min_max_length_units"]
	def INPUT_ATTR_NAMES = ["minlength","maxlength","placeholder"]
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
