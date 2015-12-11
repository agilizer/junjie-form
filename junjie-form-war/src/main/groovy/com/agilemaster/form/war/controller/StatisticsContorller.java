package com.agilemaster.form.war.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.agilemaster.form.constants.FormWarConstants;
import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.option.AnswerCacheOptions;
import com.agilemaster.form.option.HtmlFormOptions;
import com.agilemaster.form.option.HtmlInputOptions;
import com.agilemaster.form.war.service.StatisticsService;
import com.agilemaster.form.war.util.StaticMethod;

@RestController
@RequestMapping("/api/v1/statistics")
public class StatisticsContorller {
	private static final Logger log = LoggerFactory
			.getLogger(InputValueController.class);
	@Autowired
	HtmlFormOptions htmlFormOptions;
	@Autowired
	HtmlInputOptions htmlInputOptions;
	@Autowired
	AnswerCacheOptions answerCacheOptions;
	@Autowired
	StatisticsService statisticsService;
	@ResponseBody
	@RequestMapping(value="/data-mutil",method = {RequestMethod.POST})
	public Map<String, Object> dataMutil(String htmlFormIds,
			HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> result = StaticMethod.genResult();
		if(null!=htmlFormIds){
			String[] htmlFormIdArray = htmlFormIds.split(" ");
			Map resultMap = statisticsService.listValueByInputValueVo(htmlFormIdArray);
			result.put(FormWarConstants.SUCCESS, true);
			result.put(FormWarConstants.DATA, resultMap);
		}else{
			result.put(FormWarConstants.ERROR_CODE, FormWarConstants.ERROR_CODE_NOT_FOUND);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/data",method = {RequestMethod.POST})
	public Map<String, Object> data(String htmlFormId,
			HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> result = StaticMethod.genResult();
		if(null!=htmlFormId){
			Map resultMap = statisticsService.listValueByInputValueVo(htmlFormId);
			result.put(FormWarConstants.SUCCESS, true);
			result.put(FormWarConstants.DATA, resultMap);
		}else{
			result.put(FormWarConstants.ERROR_CODE, FormWarConstants.ERROR_CODE_NOT_FOUND);
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/checkRight",method = {RequestMethod.POST})
	public Boolean checkRight(String saasId,String htmlFormId, String answerId, HttpServletRequest request) {
		Boolean result = false;
		try{
			result = statisticsService.checkRight(saasId,htmlFormId, answerId);
		}catch(Exception e){
			e.printStackTrace();
		}
		log.info(result.toString());
		return result;
	}

	
}
