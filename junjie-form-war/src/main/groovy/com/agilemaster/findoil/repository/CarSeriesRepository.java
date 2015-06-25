package com.agilemaster.findoil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilemaster.findoil.domain.CarSeries;

public interface CarSeriesRepository extends JpaRepository<CarSeries, String>{
	List<CarSeries> findAllByBrandId(String brandId);
}

