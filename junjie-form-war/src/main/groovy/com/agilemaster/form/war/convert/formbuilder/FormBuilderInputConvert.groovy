package com.agilemaster.form.war.convert.formbuilder;

import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.HtmlInput.InputType;
import com.agilemaster.form.war.service.HtmlInputDataConvert;
import com.alibaba.fastjson.JSONObject;

public abstract class FormBuilderInputConvert implements HtmlInputDataConvert{

//	"label": "公司地址",
//	"field_type": "website",
//	"required": false,
//	"field_options": {},
//	"cid": "c1"
	String LABEL_BEFORE="label";
	String REQUIRED="required";
	String FIELD_OPTIONS = "field_options";
	public HtmlInput convert(InputType inputType,JSONObject jsonObject ){
		HtmlInput htmlInput = new HtmlInput();
		htmlInput.setInputType(inputType);
		htmlInput.setLabelBefore(jsonObject.getString(LABEL_BEFORE));
		def attrs = [:]
		if(jsonObject.getBoolean(REQUIRED)){
			attrs.put(REQUIRED, "true");
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
