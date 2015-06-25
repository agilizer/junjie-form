package com.agilemaster.findoil.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.Order;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.domain.WuluType;
import com.agilemaster.findoil.domain.Order.OrderStatus;
import com.agilemaster.findoil.repository.OrderRepository;
import com.agilemaster.findoil.repository.WuluTypeRepository;
import com.agilemaster.findoil.util.StaticMethod;
import com.agilemaster.share.service.JdbcPage;
import com.agilemaster.share.service.JpaShareService;

@Service
public class SellerServiceImpl implements SellerService{

	@Autowired
	UserService userService;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	JpaShareService jpaShareService;
	@Autowired
	WuluTypeRepository wuluTypeRepository;
	@Override
	public JdbcPage listOrder(int max,int offset) {
		User user = userService.currentUser();
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		String hql = "select distinct o FROM Order o join o.product p join p.user u WHERE p.user.id=:userId ";
		String countHql = "SELECT COUNT(distinct o.id)  FROM Order o join o.product p join p.user u WHERE p.user.id=:userId";
		parameterMap.clear();
		parameterMap.put("userId", user.getId());
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, countHql, max,
				offset, parameterMap);
		return jdbcPage;
	}

	@Override
	public Map<String, Object> sendGood(Long orderId,Long wuluTypeId,String wuluId) {
		Map<String, Object> result = StaticMethod.genResult();
		Order order = orderRepository.getOne(orderId);
		WuluType wuluType = wuluTypeRepository.getOne(wuluTypeId);
		if(order!=null&&null!=wuluType&&wuluId!=null){
			String hql="update Order set wuluType =? ,wuluId =?,orderStatus=? where id=?";
			jpaShareService.executeForHql(hql, wuluType,wuluId,OrderStatus.SEND,order.getId());
			result.put(FindOilConstants.SUCCESS, true);
		}
		return result;
	}

}
