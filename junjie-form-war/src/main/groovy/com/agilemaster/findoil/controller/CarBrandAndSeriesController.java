package com.agilemaster.findoil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.agilemaster.findoil.domain.CarBrand;
import com.agilemaster.findoil.domain.CarModel;
import com.agilemaster.findoil.domain.CarSeries;
import com.agilemaster.findoil.service.CarBrandAndSeriesService;

@Controller
@RequestMapping("/carBrandAndSeries")
public class CarBrandAndSeriesController {

	@Autowired
	CarBrandAndSeriesService carBrandAndSeriesService;
	@ResponseBody
	@RequestMapping(value = "/listBrand")
	public List<CarBrand> listBrand() {
		return carBrandAndSeriesService.listCarBrand();
	}
	@ResponseBody
	@RequestMapping(value = "/listSeries")
	public List<CarSeries> listSeries(String brandId) {
		return carBrandAndSeriesService.listCarSeries(brandId);
	}
	@ResponseBody
	@RequestMapping(value = "/listModel")
	public Object listModel(String seriesId) {
		return carBrandAndSeriesService.listCarModel(seriesId);
	}
	@RequestMapping(value = "/listMaintain")
	public ModelAndView search(String modelId) {
		CarModel carModel = carBrandAndSeriesService.listMaintain(modelId);
		ModelAndView model = new ModelAndView();
		model.addObject("productCatalogIds", carBrandAndSeriesService.listProductCatalog(carModel));
		model.setViewName("redirect:/product/list/10/0");
		return model;
	}
}
