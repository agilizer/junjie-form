package com.agilemaster.findoil.repository;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilemaster.findoil.domain.Order;
import com.agilemaster.findoil.domain.Order.OrderStatus;
import com.agilemaster.findoil.domain.Product;
import com.agilemaster.findoil.domain.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	List<Order> findAllByOrderStatus(OrderStatus orderStatus);
	
	List<Order> findAllByOrderStatusAndLockEndTimeBefore(OrderStatus orderStatus,Calendar calandar);

	int countByOrderStatusAndProductAndUser(OrderStatus orderStatus,Product product,User user);
	
}
