package com.agilemaster.form.service;

import java.util.Map;

public interface BaseOptions<T> {

	T save(T domain);

	T delete(T domain);

	void delete(String id);

	T update(String id, Map<String, Object> params);

	T findOne(Object id);
	
	long count();
	
	

}
