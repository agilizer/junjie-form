package com.agilemaster.form.option;

import com.agilemaster.form.domain.AnswerCache;

public interface AnswerCacheOptions extends BaseOptions<AnswerCache>{
	AnswerCache save(String saasId,String formId,String answerId,String cacheContent);
}
