package com.agilemaster.findoil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilemaster.findoil.domain.OrderAddress;
import com.agilemaster.findoil.domain.User;


public interface OrderAddressRepository extends JpaRepository<OrderAddress, Long>{
	//OrderAddress findBy
 List<OrderAddress> findAllByUserOrderByLastUpdatedDesc(User user);
}
