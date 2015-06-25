package com.agilemaster.findoil.service;

import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.domain.Order;
import com.agilemaster.findoil.domain.Order.OrderStatus;
import com.agilemaster.findoil.domain.Product;
import com.agilemaster.findoil.repository.OrderRepository;
import com.agilemaster.findoil.repository.ProductRepository;

@Service(value="productLockService")
public class OrderLockServiceImpl implements OrderLockService{

	private static final Logger log = LoggerFactory
			.getLogger(OrderLockServiceImpl.class);
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ProductRepository productRepository;
	@Scheduled(fixedRate=600000)
	@Override
	public void checkLock() {
		List<Order> lockFinishList = orderRepository.findAllByOrderStatusAndLockEndTimeBefore(OrderStatus.LOCK,
				Calendar.getInstance());
		log.info("lock lockFinishList size-->"+lockFinishList.size());
		Product product = null;
		for(Order order : lockFinishList){			
			order.setOrderStatus(OrderStatus.LOCK_FINISH);
			orderRepository.save(order);
			product = order.getProduct();
			product.setSelledNumber(product.getSelledNumber()-order.getOrderNumber());
			productRepository.save(product);
		}
	}

}
