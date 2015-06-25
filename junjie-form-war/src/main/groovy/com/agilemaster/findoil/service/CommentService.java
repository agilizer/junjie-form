package com.agilemaster.findoil.service;

import java.util.Map;

import com.agilemaster.findoil.domain.Product;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.share.service.JdbcPage;

public interface CommentService {
	
	JdbcPage listByProduct(Long productId,int max,int offset);
	boolean checkComment(Product product ,User user);
	Map<String,Object> product(Long productId,String content,String star);

}
