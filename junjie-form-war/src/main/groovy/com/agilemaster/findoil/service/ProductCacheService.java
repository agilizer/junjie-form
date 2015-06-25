package com.agilemaster.findoil.service;

import com.agilemaster.findoil.domain.ProductCache;

public interface ProductCacheService {
	void increaseComment(ProductCache productCache);
	
	void increaseOrder(ProductCache productCache);
}
