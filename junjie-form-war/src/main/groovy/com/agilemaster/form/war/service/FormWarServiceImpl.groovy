package com.agilemaster.form.war.service

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service

import com.agilemaster.form.domain.HtmlForm
import com.agilemaster.form.domain.HtmlInput
import com.agilemaster.form.option.HtmlFormOptions;
import com.alibaba.fastjson.JSON

@Service
class FormWarServiceImpl  implements FormWarService{
	@Autowired
	HtmlFormOptions htmlFormOptions;
	@Override
	public void updateJsonContentAfterCopy(HtmlForm htmlForm,
			List<HtmlInput> htmlInputList) {
			/**
			  {"fields":[{"cid":"789b993e0b7e40cbbec38e1900a55e78","field_options":{"size":"small"},"field_type":"text","id":"789b993e0b7e40cbbec38e1900a55e78",
			  "label":"请描述一下你的性格特点和喜欢的异性","required":true},{"cid":"8a67d36966e44448b5cb249e87637672","field_options":{"size":"small"},
			  "field_type":"text","id":"8a67d36966e44448b5cb249e87637672","label":"","required":true},
			  {"cid":"30d719c3995d4a55a6434a896b9bf4c6","field_options":{"size":"small"},"field_type":"text","id":"30d719c3995d4a55a6434a896b9bf4c6","label":"","required":true}]} 
			 */
			def fieldList = []
			def tempMap = [:]
			htmlInputList.each {
				tempMap = [cid:it.id,field_type:it.inputType,id:it.id,
						field_options:[size:"small"],label:it.labelBefore,sequence:it.sequence]
				if(it.inputAttrs&&it.inputAttrs.containsKey("required")){
					tempMap.put("required", true)
				}
				fieldList.add(tempMap)
			}
			fieldList = fieldList.sort{a,b->a.sequence <=> b.sequence}
			def jsoObject = [fields:fieldList]
			def jsonContent = JSON.toJSONString(jsoObject)
			htmlFormOptions.update(htmlForm.getId(),jsonContent, fieldList.size())
	}

}
