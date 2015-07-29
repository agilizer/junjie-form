package com.agilemaster.form.war;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.agilemaster.form.war.service.AuthService;

public class CorsFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		WebApplicationContext app = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getServletContext()); 
		AuthService authService = app.getBean(AuthService.class);
		if(authService.auth(req)){
			chain.doFilter(req, res);
		}else{
			response.setStatus(response.SC_FORBIDDEN);
		}
	}

	@Override
	public void destroy() {

	}

}
