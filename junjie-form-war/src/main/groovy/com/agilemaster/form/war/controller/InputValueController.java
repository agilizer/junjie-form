package com.agilemaster.form.war.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilemaster.form.constants.FormWarConstants;
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.domain.HtmlInput.InputType;
import com.agilemaster.form.domain.InputValue;
import com.agilemaster.form.option.HtmlInputOptions;
import com.agilemaster.form.option.InputValueOptions;
import com.agilemaster.form.war.util.MD5Util;
import com.agilemaster.form.war.util.StaticMethod;

@RestController
@RequestMapping("/api/v1/inputValue")
public class InputValueController {

	@Autowired
	InputValueOptions inputValueOptions;
	@Autowired
	HtmlInputOptions htmlInputOptions;

	@ResponseBody
	@RequestMapping("/create")
	public Map<String, Object> create(String htmlInputId, String answerId,
			String[] values, String[] fileInfoIds, HttpServletRequest request) {
		Map<String, Object> result = StaticMethod.genResult();
		HtmlInput htmlInput = htmlInputOptions.findOne(htmlInputId);
		if (null != htmlInput && null != answerId) {
			if (null != values && values.length > 0  || null!=fileInfoIds&&fileInfoIds.length>0) {
				String id = MD5Util.MD5(htmlInputId + answerId);
				inputValueOptions.delete(id);
				InputValue inputValue = new InputValue();
				inputValue.setId(id);
				inputValue.setAnswerId(answerId);
				inputValue.setHtmlInputId(htmlInputId);
				inputValue.setFormId(htmlInput.getFormId());
				InputType inputType = htmlInput.getInputType();
				try {
					if (values.length == 1) {
						String strValue = values[0];
						if (inputType == InputType.date) {
							// yyyy-MM-dd
							Date date = StaticMethod.parseDate(strValue);
							inputValue.setDateValue(date);
						} else if (inputType == InputType.dateTime) {
							Date date = StaticMethod.parseDate(strValue,
									"yyyy-MM-dd HH:mm");
							inputValue.setDateValue(date);
						} else if (inputType == InputType.number) {
							inputValue.setNumberValue(Long.parseLong(strValue));
						}
						inputValue.setStrValue(strValue);
					}else{
						inputValue.setListValue(Arrays.asList(values));
						inputValue.setStrValue(values.toString());
					}
				} catch (Exception e) {
					result.put(FormWarConstants.ERROR_MSG, e);
				}
				if(null!=fileInfoIds&&fileInfoIds.length>0){
					//TODO
				}
				result.put(FormWarConstants.SUCCESS, true);
			}else{
				result.put(FormWarConstants.ERROR_MSG, "inputValue 的值不能为空");
			}
		}else{
			result.put(FormWarConstants.ERROR_MSG, "htmlInputId 的值不正确");
		}
		return result;
	}
}
