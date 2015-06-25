package com.agilemaster.findoil.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.Invoice;
import com.agilemaster.findoil.domain.Invoice.InvoiceType;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.repository.InvoiceRepository;
import com.agilemaster.findoil.util.StaticMethod;
import com.agilemaster.share.service.JpaShareService;


@Service
public class InvoiceServiceImpl implements InvoiceService{
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	JpaShareService jpaShareService;
	
	@Override
	public List<Invoice> list(User user) {
		return invoiceRepository.findAllByUser(user.getId());
	}

	@Override
	public Map<String, Object> updateFiled(Long id, String fieldName,
			String fieldValue) {
		Map<String, Object> result = StaticMethod.genResult();
		Invoice invoice = invoiceRepository.findOne(id);
		if(invoice!=null){
			switch(fieldName){
			case("companyName"):invoice.setCompanyName(fieldValue);;break;
			case("number"):invoice.setNumber(fieldValue);break;
			case("invoiceAddress"):invoice.setInvoiceAddress(fieldValue);break;
			case("invoiceTel"):invoice.setInvoiceTel(fieldValue);break;
			case("bankName"):invoice.setBankName(fieldValue);break;
			case("bankNo"):invoice.setBankNo(fieldValue);break;
			default:break;
			}
			Calendar now = Calendar.getInstance();
			invoice.setLastUpdated(now);
			invoiceRepository.save(invoice);
			result.put(FindOilConstants.SUCCESS, true);
		}
		return result;
	}

	@Override
	public Invoice save(Invoice invoice) {
		User user = userService.currentUser();
		Calendar now = Calendar.getInstance();
		if(invoice.getBankName()==null||invoice.getBankName().trim().equals("")){
			invoice.setInvoiceType(InvoiceType.VAT);
		}else{
			invoice.setInvoiceType(InvoiceType.NORMAL);
		}
		invoice.setDateCreated(now);
		invoice.setLastUpdated(now);
		invoice.setUser(user);
		invoiceRepository.save(invoice)	;
		return invoice;
	}

	@Override
	public Invoice get(Long id) {
		return invoiceRepository.getOne(id);
	}
	

}
