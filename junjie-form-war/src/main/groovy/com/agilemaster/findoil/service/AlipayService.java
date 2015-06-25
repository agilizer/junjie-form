package com.agilemaster.findoil.service;

import javax.servlet.http.HttpServletRequest;

import com.agilemaster.findoil.domain.Order;

public interface AlipayService {
	public int TYPE_NOTIFY = 0;
	public int TYPE_RETURN = 1;

	String getPaymentHtml(Order order, HttpServletRequest request);

	public String orderPaymentBack(HttpServletRequest request, int type);

}
