package com.agilemaster.findoil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.agilemaster.findoil.domain.CarBrand;

public interface CarBrandRepository extends JpaRepository<CarBrand, String>{

	CarBrand findByBrandId(String brandId);
	@Query("from CarBrand order by brandLetter")
	List<CarBrand> findAllOrderByBrandLetter();
}
