package com.agilemaster.findoil.service;

import java.util.List;
import java.util.Map;

import com.agilemaster.findoil.domain.Invoice;
import com.agilemaster.findoil.domain.User;

public interface InvoiceService {
	
	public  List<Invoice> list(User user);
	
	Invoice get(Long id);
	
	Map<String, Object> updateFiled(Long id, String fieldName,
			String fieldValue);



	public Invoice save(Invoice invoice);

}
