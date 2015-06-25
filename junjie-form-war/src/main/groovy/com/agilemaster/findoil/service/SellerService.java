package com.agilemaster.findoil.service;

import java.util.Map;


import com.agilemaster.share.service.JdbcPage;

public interface SellerService {
	public JdbcPage listOrder(int max,int offset); 
	public Map<String,Object> sendGood(Long orderId,Long wuluTypeId,String wuluId);

}
