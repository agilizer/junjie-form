package com.agilemaster.form.domain;

import com.agilemaster.form.constants.JunjieFormConstants;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface HtmlInputAccessor {
	@Query("SELECT * FROM "+ JunjieFormConstants.DEFAULT_KEY_SPACE+"."+JunjieFormConstants.T_HTML_INPUT+" where formId=: formId")
	public Result<HtmlInput> listByFormId(@Param("formId")String formId);
}
