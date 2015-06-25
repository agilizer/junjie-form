package com.agilemaster.findoil.service;

import java.util.List;

import com.agilemaster.findoil.domain.CarBrand;
import com.agilemaster.findoil.domain.CarModel;
import com.agilemaster.findoil.domain.CarSeries;

public interface CarBrandAndSeriesService {
	List<CarBrand> listCarBrand();
	
	List<CarSeries> listCarSeries(String brandId);
	
	List<CarModel> listCarModel(String seriesId);
	
	CarModel listMaintain(String modelId);
	
	List<Long> listProductCatalog(CarModel carModel);
}
