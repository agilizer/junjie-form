package com.agilemaster.form.war.service;

import javax.annotation.PostConstruct

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.agilemaster.cassandra.option.CassandraTemplate
import com.agilemaster.form.constants.FormWarConstants
import com.agilemaster.form.domain.AnswerCache
import com.agilemaster.form.domain.HtmlForm
import com.agilemaster.form.domain.HtmlInput
import com.agilemaster.form.domain.InputValue
import com.agilemaster.form.domain.HtmlInput.InputType
import com.agilemaster.form.option.AnswerCacheOptions
import com.agilemaster.form.option.FileInfoOptions
import com.agilemaster.form.option.HtmlFormOptions
import com.agilemaster.form.option.HtmlInputOptions
import com.agilemaster.form.option.InputValueOptions
import com.agilemaster.form.war.convert.formanswer.InputValueConvert
import com.agilemaster.form.war.convert.formanswer.ListValueConvert
import com.agilemaster.form.war.convert.formanswer.NumberValueConvert
import com.agilemaster.form.war.convert.formanswer.TextValueConvert
import com.agilemaster.form.war.util.MD5Util
import com.agilemaster.form.war.util.StaticMethod
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject

@Service
public class AnswerDataConvertFormRender implements AnswerDataConvert{
	private static final Logger log = LoggerFactory
	.getLogger(AnswerDataConvertFormRender.class);
	@Autowired
	InputValueOptions inputValueOptions;
	@Autowired
	FileInfoOptions fileInfoOptions;
	@Autowired
	HtmlFormOptions htmlFormOptions;
	@Autowired
	HtmlInputOptions htmlInputOptions;
	@Autowired
	AnswerCacheOptions answerCacheOptions;
	@Autowired
	CassandraTemplate cassandraTemplate;
	private Map<String,InputValueConvert> inputValutConvertMap = new HashMap<String,InputValueConvert>();
	@PostConstruct
	public void initInputDataConvert(){
		inputValutConvertMap.put(InputType.text, new TextValueConvert())
		inputValutConvertMap.put(InputType.paragraph, new TextValueConvert())
		inputValutConvertMap.put(InputType.email, new TextValueConvert())
		inputValutConvertMap.put(InputType.website, new TextValueConvert())
		inputValutConvertMap.put(InputType.checkboxes, new ListValueConvert())
		inputValutConvertMap.put(InputType.radio, new TextValueConvert())
		inputValutConvertMap.put(InputType.number, new NumberValueConvert())
		inputValutConvertMap.put(InputType.progress, new NumberValueConvert())
	}
	@Override
	public Map<String, Object> answerForm(String saasId,String htmlFormId,String answerId,String jsonAnswer) {
		Map<String,Object> result = StaticMethod.genResult();
		HtmlForm htmlForm = htmlFormOptions.findOne(htmlFormId);
		if(null!=htmlForm){
			//{"9efe468630df4e0ea87094e0ac42b04c":"木",
			//"aad908afb01742e9a2bc58a649b7fb22":"工 ","2a99da85940f4631a8a31ac1c4608415":{"0":"on","1":false}}
			JSONObject jsonAnswerObject = JSON.parseObject(jsonAnswer);
			List<HtmlInput> inputs = htmlInputOptions.listByFormId(htmlFormId);
			String answerCacheId = MD5Util.MD5(saasId+htmlFormId+answerId);
			AnswerCache answerCache = answerCacheOptions.findOne(answerCacheId);
			if(answerCache == null){
				answerCache = answerCacheOptions.save(saasId, htmlFormId, answerId,htmlForm.getJsonContent());
			}
			JSONObject jsonAnswerCacheObject =  JSON.parseObject(answerCache.getFormRenderCache());
			JSONArray jsonAnswerArray = jsonAnswerCacheObject.getJSONArray("fields");
			def jsonAnswerMap = [:]
			jsonAnswerArray.each {
				jsonAnswerMap.put(it.id, it);
			}
			String htmlInputId = ""
			for(HtmlInput input:inputs){
				Object answerResult = jsonAnswerObject.get(input.getId());
				if(null!= answerResult){
					htmlInputId = input.getId()
					String inputValueId = MD5Util.MD5(htmlInputId+answerId);
					InputValue inputValue = inputValueOptions.findOne(inputValueId);
					Date dateCreated = null;
					if(inputValue!=null){
						dateCreated = new Date();
					}
					InputValueConvert convert = inputValutConvertMap.get(input.getInputType());
					if(convert){
						inputValue = convert.convert(input, answerId, inputValueId, answerResult, dateCreated,jsonAnswerMap.remove(htmlInputId));
						inputValueOptions.save(inputValue);
					}
				}
			}
			def updateMap = ["formRenderCache":JSON.toJSONString(jsonAnswerCacheObject)]
			if(jsonAnswerMap.size()==0){
				updateMap.put("finish", true);
				updateMap.put("endAnswerTime", new Date());
			}
			log.info("bootstrapData====>"+updateMap)
			log.info("answerCache====>isFinis-->"+answerCache.isFinish()+"   ---jsonAnswerMap "+jsonAnswerMap.size())
			answerCacheOptions.update(answerCacheId, updateMap)
			result.put(FormWarConstants.SUCCESS, true)
		}else{
			result.put(FormWarConstants.ERROR_MSG, "htmlForm没有找到!")
			result.put(FormWarConstants.ERROR_CODE, FormWarConstants.ERROR_CODE_NOT_FOUND);
		}
		return result;
	}

}
