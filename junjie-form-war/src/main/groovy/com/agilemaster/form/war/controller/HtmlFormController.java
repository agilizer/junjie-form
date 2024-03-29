package com.agilemaster.form.war.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilemaster.form.constants.FormWarConstants;
import com.agilemaster.form.domain.AnswerCache;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.domain.HtmlInput;
import com.agilemaster.form.option.AnswerCacheOptions;
import com.agilemaster.form.option.HtmlFormOptions;
import com.agilemaster.form.option.HtmlInputOptions;
import com.agilemaster.form.util.MD5Util;
import com.agilemaster.form.war.service.FormWarService;
import com.agilemaster.form.war.service.HtmlFormDataConvert;
import com.agilemaster.form.war.util.StaticMethod;

@RestController
@RequestMapping("/api/v1/htmlForm")
public class HtmlFormController {
	private static final Logger log = LoggerFactory
			.getLogger(HtmlFormController.class);
	@Autowired
	HtmlFormOptions htmlFormOptions;
	@Autowired
	FormWarService formWarService;
	@Autowired
	HtmlInputOptions htmlInputOptions;
	@Autowired
	AnswerCacheOptions answerCacheOptions;
	@Autowired
	@Qualifier("htmlFormDataConvertFormBuilder")
	HtmlFormDataConvert htmlFormDataConvert;
	@ResponseBody
	@RequestMapping("/create")
	public Map<String, Object> create(HtmlForm htmlForm,String parentHtmlFormId,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
			HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> result = StaticMethod.genResult();
		log.info("create-parentHtmlFormId--> "+parentHtmlFormId+"\n"+htmlForm.getJsonContent());
		if (htmlForm != null) {
			htmlForm.setStartTime(startDate);
			htmlForm.setEndTime(endDate);
			htmlForm = htmlFormOptions.save(htmlForm);
			if(null!=parentHtmlFormId&&!"".equals(parentHtmlFormId.trim())){
				Map<String,Object> updateMap = new HashMap<String,Object>();
				updateMap.put("childrenFormId", htmlForm.getId());
				htmlFormOptions.update(parentHtmlFormId, updateMap);
			}
			if(htmlForm.getJsonContent()!=null){
				htmlForm= htmlFormDataConvert.convert(htmlForm.getId(), htmlForm.getJsonContent());
			}
			result.put(FormWarConstants.SUCCESS, true);
			result.put(FormWarConstants.DATA, htmlForm);
		}else{
			result.put(FormWarConstants.ERROR_MSG, "信息不正确");
			result.put(FormWarConstants.DATA, htmlForm);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/createFormBuilder")
	public Map<String, Object> createFormBuilder(String htmlFormId,String jsonContent,
			HttpServletRequest request,HttpServletResponse response) {
		log.info("createFormBuilder---> "+htmlFormId+"\n"+jsonContent);
		Map<String, Object> result = StaticMethod.genResult();
		HtmlForm htmlForm= htmlFormDataConvert.convert(htmlFormId, jsonContent);
		if(null!=htmlForm){
			result.put(FormWarConstants.SUCCESS, true);
			result.put(FormWarConstants.DATA, htmlForm);
		}else{
			result.put(FormWarConstants.ERROR_CODE, FormWarConstants.ERROR_CODE_NOT_FOUND);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/update")
	public Map<String, Object> update( HtmlForm htmlForm,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate) {
		Map<String, Object> result = StaticMethod.genResult();
		if (htmlForm != null&&htmlForm.getId()!=null) {
			htmlForm.setStartTime(startDate);
			htmlForm.setEndTime(endDate);
			htmlForm.setLastUpdated(new Date());
			String updateId = htmlForm.getId();
			HtmlForm oldForm = htmlFormOptions.findOne(htmlForm.getId());
			htmlForm.setJsonContent(oldForm.getJsonContent());
			htmlFormOptions.delete(updateId);
			htmlForm.setId(updateId);
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
		}else{
			result.put(FormWarConstants.ERROR_MSG, "saasId和htmlFormId不能为空");
			result.put(FormWarConstants.ERROR_CODE, FormWarConstants.ERROR_CODE_NULL);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/copy")
	public Map<String, Object> copy(String saasId,String htmlFormId) {
		Map<String, Object> result = StaticMethod.genResult();
		if (null != saasId && htmlFormId != null) {
			HtmlForm htmlForm = htmlFormOptions.copyAndSave(htmlFormId);
			if(htmlForm!=null){
				List<HtmlInput> htmlInputList = htmlInputOptions.copyHtmlInputs(htmlFormId, htmlForm.getId());
				formWarService.updateJsonContentAfterCopy(htmlForm, htmlInputList);
				result.put(FormWarConstants.DATA,htmlForm);
				result.put(FormWarConstants.SUCCESS, true);
			}else{
				result.put(FormWarConstants.ERROR_MSG, "没有找到相关数据");
				result.put(FormWarConstants.ERROR_CODE, FormWarConstants.ERROR_CODE_NOT_FOUND);
			}
		}else{
			result.put(FormWarConstants.ERROR_MSG, "saasId和htmlFormId不能为空");
			result.put(FormWarConstants.ERROR_CODE, FormWarConstants.ERROR_CODE_NULL);
		}
		return result;
	}
	/**
	 * AnswerCache id 必须重新计算，保证saas唯一性。
	 * @param saasId
	 * @param htmlFormId
	 * @param answerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/showFormrender/{answerId}")
	public Map<String, Object> showFormrender(String saasId,String htmlFormId,@PathVariable("answerId") String answerId) {
		Map<String, Object> result = StaticMethod.genResult();
		if (null != saasId && htmlFormId != null) {
			String answerCacheId = MD5Util.MD5(saasId+htmlFormId+answerId);
			AnswerCache answerCache = answerCacheOptions.findOne(answerCacheId);
			if(answerCache == null){
				HtmlForm htmlForm = htmlFormOptions.findOne(htmlFormId);
				if(htmlForm!=null){
					answerCache = answerCacheOptions.save(saasId, htmlFormId, answerId,htmlForm.getJsonContent());
				}
			}
			result.put(FormWarConstants.DATA,answerCache);
			result.put(FormWarConstants.SUCCESS, true);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/showFormrender")
	public Map<String, Object> showFormrender(String saasId,String htmlFormId) {
		Map<String, Object> result = StaticMethod.genResult();
		if (null != saasId && htmlFormId != null) {
			HtmlForm htmlForm = htmlFormOptions.findOne(htmlFormId);
			result.put(FormWarConstants.DATA,htmlForm);
			result.put(FormWarConstants.SUCCESS, true);
		}
		return result;
	}
}
