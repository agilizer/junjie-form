package com.agilemaster.form.war.service;

import javax.servlet.ServletRequest;

public interface AuthService {
	boolean auth(ServletRequest req);
}
