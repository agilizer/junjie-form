package com.agilemaster.form.war.service;

import java.util.Map;


public interface StatisticsService {
	Map listValueByInputValueVo(String[] htmlFormId);
	boolean checkRight (String saasId,String htmlFormId,String answerId);
}
