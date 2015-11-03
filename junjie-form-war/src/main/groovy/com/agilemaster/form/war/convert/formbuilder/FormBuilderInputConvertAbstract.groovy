package com.agilemaster.form.war.convert.formbuilder;

import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.HtmlInput.InputType;
import com.alibaba.fastjson.JSONObject;

public abstract class FormBuilderInputConvertAbstract implements HtmlInputDataConvert{

//	"label": "公司地址",
//	"field_type": "website",
//	"required": false,
//	"field_options": {},
//	"cid": "c1"
	String LABEL_BEFORE="label";
	String REQUIRED="required";
	String FIELD_OPTIONS = "field_options";
	String RIGHT_VALUE="right_value";
	String SEQUENCE="sequence";
	String DESCRIPTION="description"
	public HtmlInput convert(InputType inputType,JSONObject jsonObject ){
		HtmlInput htmlInput = new HtmlInput();
		htmlInput.setInputType(inputType);
		htmlInput.setLabelBefore(jsonObject.getString(LABEL_BEFORE));
		def attrs = [:]
		if(jsonObject.getBoolean(REQUIRED)){
			attrs.put(REQUIRED, "true");
		}
		if(jsonObject.getString(RIGHT_VALUE)){
			htmlInput.setRightAnswer(jsonObject.getString(RIGHT_VALUE));
		}
		if(jsonObject.getInteger(SEQUENCE)){
			htmlInput.setSequence(jsonObject.getInteger(SEQUENCE));
		}
		if(jsonObject.getString(DESCRIPTION)){
			htmlInput.setLabelAfter(jsonObject.getString(DESCRIPTION))
		}
		if(attrs.size()>0){
			htmlInput.setInputAttrs(attrs)
		}
		htmlInput = convertOtherData(htmlInput,inputType,jsonObject.getJSONObject(FIELD_OPTIONS))
		return htmlInput;
	}
	/**
	 * jsonObject.field_options
	 * @param htmlInput
	 * @param inputType
	 * @param jsonObject
	 * @return
	 */
	public abstract HtmlInput convertOtherData(HtmlInput htmlInput,InputType inputType,JSONObject jsonObject);
	
}
