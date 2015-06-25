package com.agilemaster.findoil.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.agilemaster.findoil.domain.Invoice;
import com.agilemaster.findoil.domain.Order;
import com.agilemaster.findoil.domain.OrderAddress;
import com.agilemaster.findoil.domain.Product;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.share.service.JdbcPage;

public interface OrderService {
	String PAYMENT_FILE_DIR="/upload/payment/";
	
	public Order create(Product product,Long orderNumber,OrderAddress orderAddress,Invoice invoice);
	Order create(Long productId,Long orderNumber);
	Order get(Long id);
	
	
	public Map<String,Object > uploadPaymentFile(Long orderId,MultipartFile file);
	
	public Map<String,Object > check(Long id,Boolean result,User user);
	
	public JdbcPage list(Map<String,Object> parameterMap,User use);
	
	JdbcPage checkPaymentList(Integer max,Integer offset);
	
	public JdbcPage list(int max,int offset,Map<String,Object> parameterMap,User user);
	void update(Order order,OrderAddress orderAddress,Invoice invoice);

}
