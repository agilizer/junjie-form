package com.agilemaster.form.war.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.agilemaster.form.domain.HtmlInput
import com.agilemaster.form.option.HtmlFormOptions
import com.agilemaster.form.option.HtmlInputOptions
import com.agilemaster.form.option.InputValueOptions
import com.agilemaster.form.war.vo.InputValueVo
import com.datastax.driver.core.Row

@Service
public class StatisticsServiceImpl  implements StatisticsService{

	private static final Logger log = LoggerFactory
	.getLogger(StatisticsServiceImpl.class);
	@Autowired
	InputValueOptions inputValueOptions;
	@Autowired
	HtmlFormOptions htmlFormOptions;
	@Autowired
	HtmlInputOptions htmlInputOptions;
	@Override
	public Map listValueByInputValueVo(String[] htmlFormIds) {
		def resultMap = [:] ////key answerId value answerRow
		def inputMap = [:]
		def answerRow = null
		def answerId = ""
		def htmlInputId = ""
		htmlFormIds.each{htmlFormId->
			if(htmlFormId&&htmlFormId.trim()!=""){
				List<Row> inputValueList = inputValueOptions.listByHtmlForm(htmlFormId)
				if(inputValueList){
					//select answerId,htmlInputId,strValue,answerRight
					List<HtmlInput> inputs = htmlInputOptions.listByFormId(htmlFormId)
					inputs.each{HtmlInput input->
						inputMap.put(input.getId(), input.getLabelBefore());
					}
					inputValueList.each {row->
						answerId = row.getString("answerId")
						answerRow = resultMap.get(answerId)
						if(answerRow==null){
							answerRow = [:]
							resultMap.put(answerId, answerRow)
						}
						htmlInputId = row.getString("htmlInputId")
						answerRow.put(htmlInputId, row.getString("strValue"))
					}
				}
			}
		}
		resultMap.put("htmlInputInfo", inputMap);
		return resultMap;
	}
}
