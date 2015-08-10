package com.agilemaster.form.option;

import com.agilemaster.cassandra.option.BaseOptions;
import com.agilemaster.form.domain.HtmlForm;


public interface HtmlFormOptions extends BaseOptions<HtmlForm> {
		void update(String id,String jsonContent,int inputCount);
}
