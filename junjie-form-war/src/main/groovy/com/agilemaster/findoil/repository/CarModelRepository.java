package com.agilemaster.findoil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilemaster.findoil.domain.CarModel;

public interface CarModelRepository extends JpaRepository<CarModel, String>{
	List<CarModel> findAllBySeriesId(String seriesId);
}

