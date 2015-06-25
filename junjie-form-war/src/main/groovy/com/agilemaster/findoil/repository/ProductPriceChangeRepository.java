package com.agilemaster.findoil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.agilemaster.findoil.domain.Product;
import com.agilemaster.findoil.domain.ProductPriceChange;
import com.agilemaster.findoil.domain.ProductPriceChange.ChangeStatus;

public interface ProductPriceChangeRepository extends JpaRepository<ProductPriceChange, Long>,JpaSpecificationExecutor<Product>{
	
	List<ProductPriceChange> findAllByChangeStatus(ChangeStatus changeStatus);

}