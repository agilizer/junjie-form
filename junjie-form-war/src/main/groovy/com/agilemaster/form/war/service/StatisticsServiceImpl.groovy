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
	public List listValueByInputValueVo(String[] htmlFormIds) {
		def resultList = []
		htmlFormIds.each{htmlFormId->
			if(htmlFormId&&htmlFormId.trim()!=""){
				List<Row> inputValueList = inputValueOptions.listByHtmlForm(htmlFormId)
				if(inputValueList){
					//select answerId,htmlInputId,strValue,answerRight
					List<HtmlInput> inputs = htmlInputOptions.listByFormId(htmlFormId)
					def inputMap = [:]
					inputs.each{HtmlInput input->
						inputMap.put(input.getId(), input.getLabelBefore());
					}
					def answerMap = [:]   //key answerId value answerRow
					def answerRow = [:] //
					def inputValue = [:]//key is htmlInputId ,value is answerRow
					def answerId = ""
					def htmlInputId = ""
					inputValueList.each {row->
						answerId = row.getString("answerId")
						htmlInputId = row.getString("htmlInputId")
						answerRow = [i:answerId,
							h:htmlInputId,
							s:row.getString("strValue")
							]
						inputValue = answerMap.get(answerId)
						if(inputValue == null){
							inputValue	=	[:]
							answerMap.put(answerId, inputValue)
						}
						inputValue.put(htmlInputId,answerRow)
					}
					answerMap.put("htmlInputInfo", inputMap);
					resultList.add(answerMap)
				}
			}
		}
		return resultList;
	}
}
