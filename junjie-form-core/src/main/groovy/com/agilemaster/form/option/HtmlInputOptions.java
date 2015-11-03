package com.agilemaster.form.option;

import java.util.List;

import com.agilemaster.cassandra.option.BaseOptions;
import com.agilemaster.form.domain.HtmlInput;

public interface HtmlInputOptions extends BaseOptions<HtmlInput> {
	void deleteByFormId(String formId);
	List<HtmlInput> listByFormId(String formId);
	/**
	 * 返回复制的 {@link HtmlInput } 个数
	 * @param oldFormId
	 * @param newFormId
	 * @return 新的{@link HtmlInput }  list
	 */
	List<HtmlInput> copyHtmlInputs(String oldFormId,String newFormId);
}
