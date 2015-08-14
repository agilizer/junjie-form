package com.agilemaster.form.option;

import com.agilemaster.cassandra.option.BaseOptions;
import com.agilemaster.form.domain.HtmlForm;


public interface HtmlFormOptions extends BaseOptions<HtmlForm> {
		void update(String id,String jsonContent,int inputCount);
		/**
		 * just  copy and save {@link HtmlForm},note  not copy {@link HtmlInput}.
		 * copy  {@link HtmlInput}   @see HtmlInputOptions#copyHtmlInputs(String, String)
		 * @param fromId
		 * @return
		 */
		HtmlForm copyAndSave(String formId);
}
