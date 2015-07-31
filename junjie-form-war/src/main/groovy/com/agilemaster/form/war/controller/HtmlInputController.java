package com.agilemaster.form.war.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilemaster.form.constants.FormWarConstants;
import com.agilemaster.form.constants.JunjieFormConstants;
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.option.HtmlInputOptions;
import com.agilemaster.form.war.util.StaticMethod;

@RestController
@RequestMapping("/api/v1/htmlInput")
public class HtmlInputController {
	
	@Autowired
	HtmlInputOptions htmlInputOptions;

	@ResponseBody
	@RequestMapping("/create")
	public Map<String, Object> create(String saasId,String formId,HtmlInput htmlInput,HttpServletRequest request){
		Map<String,Object> result = StaticMethod.genResult();
		Map<String,String> inputAttrs = new HashMap<String,String>();
		for(String attr:JunjieFormConstants.INPUT_ATTRS){
			String value = request.getParameter(attr);
			if(null!=value){
				inputAttrs.put(attr, value);
			}
		}
		htmlInput.setSaasId(saasId);
		htmlInput.setFormId(formId);
		htmlInput.setInputAttrs(inputAttrs);
		htmlInputOptions.save(htmlInput);
		if(htmlInput.getId()!=null){
			result.put(FormWarConstants.SUCCESS, true);
			result.put(FormWarConstants.DATA, htmlInput.getId());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/show")
	public HtmlInput show(String id){
		return htmlInputOptions.findOne(id);
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Map<String,Object> update(String saasId,String formId,HtmlInput htmlInput,HttpServletRequest request){
		Map<String, Object> result = StaticMethod.genResult();
		if (htmlInput != null&&htmlInput.getId()!=null) {
			htmlInput.setLastUpdated(new Date());
			String updateId = htmlInput.getId();
			HtmlInput old = htmlInputOptions.findOne(updateId);
			if(old!=null&&old.getSaasId().equals(saasId)&&old.getFormId().equals(formId)){
				htmlInputOptions.delete(updateId);
				htmlInputOptions.save(htmlInput);
				result.put(FormWarConstants.SUCCESS, true);
				result.put(FormWarConstants.DATA, htmlInput);
			}else{
				result.put(FormWarConstants.ERROR_MSG, "saasId or formId or htmlInput.id wrong!");
			}
		}else{
			result.put(FormWarConstants.ERROR_MSG, "htmlInput.id wrong!");
		}
		return result;
	}
	
}
