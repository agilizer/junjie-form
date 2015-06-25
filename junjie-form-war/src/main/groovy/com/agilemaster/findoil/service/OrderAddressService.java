package com.agilemaster.findoil.service;

import java.util.List;
import java.util.Map;

import com.agilemaster.findoil.domain.OrderAddress;
import com.agilemaster.findoil.domain.User;

public interface OrderAddressService {
	
	public List<OrderAddress> list(User user);
	
	OrderAddress save(OrderAddress orderAddress);
	
	public Map<String,Object> updateFiled(Long id,String fieldName,String fieldValue);
	
	OrderAddress get(Long id);

}
