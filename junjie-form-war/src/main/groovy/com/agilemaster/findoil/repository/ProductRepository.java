package com.agilemaster.findoil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.agilemaster.findoil.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>,JpaSpecificationExecutor<Product>{
	
	
}
