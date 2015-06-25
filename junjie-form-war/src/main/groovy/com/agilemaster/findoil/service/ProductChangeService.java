package com.agilemaster.findoil.service;

import java.util.Map;

import com.agilemaster.findoil.domain.ProductPriceChange.ChangeStatus;
import com.agilemaster.share.service.JdbcPage;

public interface ProductChangeService {
	public Map<String,Object> submitChangePrice(Long productId,Float changePrice);
	public Map<String,Object> checkChangePrice(Long id,Boolean result);
	JdbcPage checkList(int max,int offset,ChangeStatus changeStatus);
}
