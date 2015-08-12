package com.agilemaster.form.war.service;

import javax.annotation.PostConstruct

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.agilemaster.cassandra.option.CassandraTemplate
import com.agilemaster.form.domain.HtmlForm
import com.agilemaster.form.domain.HtmlInput
import com.agilemaster.form.domain.HtmlInput.InputType
import com.agilemaster.form.option.HtmlFormOptions
import com.agilemaster.form.option.HtmlInputOptions
import com.agilemaster.form.war.convert.formbuilder.CheckboxesAndRadiosInputConvert
import com.agilemaster.form.war.convert.formbuilder.NumberInputConvert
import com.agilemaster.form.war.convert.formbuilder.TextInputConvert
import com.agilemaster.form.war.vo.FiledType
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject

/**
 * convert data form https://github.com/agilizer/formbuilder
 * @author asdtiang
 *
 */
@Service("htmlFormDataConvertFormBuilder")
public class HtmlFormDataConvertFormBuilder implements HtmlFormDataConvert{
	private static final Logger log = LoggerFactory
			.getLogger(HtmlFormDataConvertFormBuilder.class);
	@Autowired
	HtmlFormOptions htmlFormOptions;
	@Autowired
	HtmlInputOptions htmlInputOptions;
	@Autowired
	CassandraTemplate cassandraTemplate;
	private Map<InputType,HtmlInputDataConvert> inputDataConvertMap = new HashMap<InputType,HtmlInputDataConvert>();
	
	@PostConstruct
	public void initInputDataConvert(){
		inputDataConvertMap.put(InputType.text, new TextInputConvert())
		inputDataConvertMap.put(InputType.email, new TextInputConvert())
		inputDataConvertMap.put(InputType.website, new TextInputConvert())
		inputDataConvertMap.put(InputType.checkbox, new CheckboxesAndRadiosInputConvert())
		inputDataConvertMap.put(InputType.radio, new CheckboxesAndRadiosInputConvert())
		inputDataConvertMap.put(InputType.number, new NumberInputConvert())
	}
	@Override
	public HtmlForm convert(String htmlFormId, String jsonContent) {
		HtmlForm htmlForm = htmlFormOptions.findOne(htmlFormId);
		if(htmlForm!=null){
			JSONObject object =  JSON.parseObject(jsonContent);
			JSONArray array = object.getJSONArray("fields");
			InputType inputType = null
			HtmlInputDataConvert htmlInputDataConvert = null
			HtmlInput htmlInput = null
			int sequence = 1;
			htmlInputOptions.deleteByFormId(htmlFormId);
			int inputCount = 0;
			array.each {
				htmlInputDataConvert = null
				htmlInput = null
				inputType  = null
				inputType = InputType.valueOf(it.field_type)
				if(inputType!=null){
					htmlInputDataConvert = inputDataConvertMap.get(inputType)
					htmlInput = htmlInputDataConvert.convert(inputType, it);
					htmlInput.setSequence(sequence*5);
					sequence = sequence +1;
					htmlInput.setFormId(htmlForm.getId())
					htmlInputOptions.save(htmlInput)
					it.put("cid", htmlInput.getId())
					it.put("id", htmlInput.getId())
					inputCount++;
				}
			}
			htmlFormOptions.update(htmlFormId, JSON.toJSONString(object), inputCount);
		}else{
			log.warn("htmlFormId not found : {}",htmlFormId)
		}
		return htmlForm;
	}
	@Override
	public void registerHtmlInputConvert(InputType inputType,
			HtmlInputDataConvert inputDataConvert) {
			inputDataConvertMap.put(inputType, inputDataConvert)
	}

}
