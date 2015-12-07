package com.agilemaster.form.war.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Contact;

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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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


@Api(value = "htmlForm-api")  
@RestController
@RequestMapping("/api/v1/")
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
	
	@ApiOperation(value = "创建表单", notes = "返回创建结果对象", response = Map.class)  
	@ResponseBody
	@RequestMapping(value ="/form",method = {RequestMethod.POST},produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Map<String, Object> create(@RequestParam HtmlForm htmlForm,@RequestParam  String parentHtmlFormId, @RequestParam String saasId,
			@RequestParam String accessKey, 
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date endDate,
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
	@RequestMapping(value="/formCreateByJsonContent"  ,method = {RequestMethod.POST})
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
	@RequestMapping(value="/update",method = {RequestMethod.POST,RequestMethod.GET})
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
	@RequestMapping(value="/form",method = {RequestMethod.DELETE})
	public Map<String, Object> delete(String saasId, String htmlFormId) {
		Map<String, Object> result = StaticMethod.genResult();
		if (null != saasId && htmlFormId != null) {
			htmlFormOptions.delete(htmlFormId);
			result.put(FormWarConstants.SUCCESS, true);
		}
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/list",method = {RequestMethod.POST,RequestMethod.GET})
	public Map<String, Object> list(String saasId,Long max,Long offset) {
		Map<String, Object> result = StaticMethod.genResult();
		//TODO 
		return result;
	}
	@ResponseBody
	@RequestMapping(value="/form",method = {RequestMethod.GET})
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
	@ApiOperation(value = "根据id复制表单", notes = "返回复制表单结果对象", response = Map.class)  
	@ResponseBody
	@RequestMapping(value="/copy",method = {RequestMethod.POST,RequestMethod.GET},produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Map<String, Object> copy(@ApiParam(name="saasId",value = "saasId字符串", required = true,defaultValue="946c4eea-15cb-4dfb-8f8d-91b99fe78939") @RequestParam String saasId
			,@ApiParam(name="htmlFormId",value = "需要复制的表单id", required = true) @RequestParam String htmlFormId,
			@ApiParam(name="accessKey",value = "访问api时需要的key", required = true,defaultValue="c8d47cffb16e4668bc84b3b4f9f72023") @RequestParam String accessKey) {
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
	@RequestMapping(value="/showFormrender/{answerId}",method = {RequestMethod.POST,RequestMethod.GET})
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
	@RequestMapping(value="/showFormrender",method = {RequestMethod.POST,RequestMethod.GET})
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
