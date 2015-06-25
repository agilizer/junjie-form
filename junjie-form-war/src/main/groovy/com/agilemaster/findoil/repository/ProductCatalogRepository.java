package com.agilemaster.findoil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.agilemaster.findoil.domain.ProductCatalog;

public interface ProductCatalogRepository extends JpaRepository<ProductCatalog, Long>{
	@Query("select max(sequence) from ProductCatalog")
	int maxSequence();
}
