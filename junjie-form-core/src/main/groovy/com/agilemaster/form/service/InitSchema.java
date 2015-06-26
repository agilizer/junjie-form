package com.agilemaster.form.service;

import com.datastax.driver.core.Session;


public interface InitSchema {
	void init(Session session);
}
