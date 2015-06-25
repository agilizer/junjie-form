package com.agilemaster.findoil.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.OrderAddress;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.repository.OrderAddressRepository;
import com.agilemaster.findoil.util.StaticMethod;
import com.agilemaster.share.service.JpaShareService;


@Service
public class OrderAddressServiceImpl implements OrderAddressService {
	
	@Autowired
	OrderAddressRepository orderAddressRepository;
	
	@Autowired
	JpaShareService jpaShareService;
	@Autowired
	UserService userService;
	@Override
	public List<OrderAddress> list(User user) {
		return orderAddressRepository.findAllByUserOrderByLastUpdatedDesc(user);
	}

	@Override
	public OrderAddress save(OrderAddress orderAddress) {
		Calendar now = Calendar.getInstance();
		User user = userService.currentUser();
		orderAddress.setDateCreated(now);
		orderAddress.setLastUpdated(now);
		orderAddress.setUser(user);
		return orderAddressRepository.save(orderAddress);
	}

	
	@Override
	public Map<String, Object> updateFiled(Long id, String fieldName,
			String fieldValue) {
		Map<String, Object> result = StaticMethod.genResult();
		OrderAddress address = orderAddressRepository.findOne(id);
		if(address!=null){
			switch(fieldName){
			case("address"):address.setAddress(fieldValue);break;
			case("tel"):address.setTel(fieldValue);break;
			case("name"):address.setName(fieldValue);break;
			default:break;
			}
			Calendar now = Calendar.getInstance();
			address.setLastUpdated(now);
			orderAddressRepository.save(address);
			result.put(FindOilConstants.SUCCESS, true);
		}
		return result;
	}

	@Override
	public OrderAddress get(Long id) {
		return orderAddressRepository.findOne(id);
	}

}
