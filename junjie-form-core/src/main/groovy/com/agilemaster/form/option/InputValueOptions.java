package com.agilemaster.form.option;

import java.util.List;

import com.agilemaster.cassandra.option.BaseOptions;
import com.agilemaster.form.domain.InputValue;

public interface InputValueOptions extends BaseOptions<InputValue>{
	List<?> listByHtmlForm(String htmlFormId);
}
