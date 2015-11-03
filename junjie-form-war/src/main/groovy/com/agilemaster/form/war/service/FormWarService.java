package com.agilemaster.form.war.service;

import java.util.List;

import com.agilemaster.form.domain.HtmlForm;
import com.agilemaster.form.domain.HtmlInput;

public interface FormWarService {
	/**
	 * 
	 * @param htmlForm
	 * @param htmlInputList copy之后的inputList
	 */
	void updateJsonContentAfterCopy(HtmlForm htmlForm,List<HtmlInput>  htmlInputList);
	
	
}
