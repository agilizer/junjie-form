package com.agilemaster.form.war.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilemaster.form.constants.FormWarConstants;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.option.HtmlFormOptions;
import com.agilemaster.form.war.util.StaticMethod;

@RestController
@RequestMapping("/api/v1/htmlForm")
public class HtmlFormController {

	@Autowired
	HtmlFormOptions htmlFormOptions;

	@ResponseBody
	@RequestMapping("/create")
	public Map<String, Object> create(String saasId, HtmlForm htmlForm) {
		Map<String, Object> result = StaticMethod.genResult();
		if (null != saasId && htmlForm != null) {
			htmlForm.setSaasId(saasId);
			htmlForm = htmlFormOptions.save(htmlForm);
			result.put(FormWarConstants.SUCCESS, true);
			result.put(FormWarConstants.DATA, htmlForm);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update(String saasId, HtmlForm htmlForm) {
		Map<String, Object> result = StaticMethod.genResult();
		if (null != saasId && htmlForm != null&&htmlForm.getId()!=null) {
			htmlForm.setSaasId(saasId);
			String updateId = htmlForm.getId();
			htmlFormOptions.delete(updateId);
			htmlFormOptions.save(htmlForm);
			result.put(FormWarConstants.SUCCESS, true);
			result.put(FormWarConstants.DATA, htmlForm);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(String saasId, String htmlFormId) {
		Map<String, Object> result = StaticMethod.genResult();
		if (null != saasId && htmlFormId != null) {
			htmlFormOptions.delete(htmlFormId);
			result.put(FormWarConstants.SUCCESS, true);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(String saasId,Long max,Long offset) {
		Map<String, Object> result = StaticMethod.genResult();
		//TODO 
		return result;
	}
	@ResponseBody
	@RequestMapping("/show")
	public Map<String, Object> show(String saasId,String htmlFormId) {
		Map<String, Object> result = StaticMethod.genResult();
		if (null != saasId && htmlFormId != null) {
			HtmlForm htmlForm = htmlFormOptions.findOne(htmlFormId);
			result.put(FormWarConstants.DATA,htmlForm);
			result.put(FormWarConstants.SUCCESS, true);
		}
		return result;
	}
}
