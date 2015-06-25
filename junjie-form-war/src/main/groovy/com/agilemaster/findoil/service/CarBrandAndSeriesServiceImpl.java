package com.agilemaster.findoil.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.bootstrap.CarBrandInitService;
import com.agilemaster.findoil.domain.CarBrand;
import com.agilemaster.findoil.domain.CarModel;
import com.agilemaster.findoil.domain.CarSeries;
import com.agilemaster.findoil.repository.CarBrandRepository;
import com.agilemaster.findoil.repository.CarModelRepository;
import com.agilemaster.findoil.repository.CarSeriesRepository;
import com.agilemaster.share.service.JpaShareService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class CarBrandAndSeriesServiceImpl implements CarBrandAndSeriesService{

	private Map<String,Object> searchCache=new HashMap<String,Object>();
	private String key="73bb3a4f422bffbf4511ccadd238e1f5";
	@Autowired
	CarBrandRepository carBrandRepository;
	@Autowired
	CarSeriesRepository carSeriesRepository;
	@Autowired
	CarModelRepository carModelRepository;
	@Autowired
	JpaShareService jpaShareService;
	
	@Override
	public List<CarBrand> listCarBrand() {
		return carBrandRepository.findAllOrderByBrandLetter();
	}

	@Override
	public List<CarSeries> listCarSeries(String brandId) {
		return carSeriesRepository.findAllByBrandId(brandId);
	}

	@Override
	public List<CarModel> listCarModel(String seriesId) {
		List<CarModel> modelList = carModelRepository.findAllBySeriesId(seriesId);
		if(modelList.size()==0){
			String url="http://api2.juheapi.com/carzone/car/model/list?key="+key+"&sid="+seriesId;
			String charset ="UTF-8";
			String jsonResult="";
			jsonResult = CarBrandInitService.get(url, charset);//得到JSON字符串
			System.out.println("jsonResult--->"+jsonResult+url);
			JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(jsonResult);
			JSONArray jsonArray =  jsonObject.getJSONArray("result");
			JSONObject temp = null;
			CarModel modelTemp = null;
			for(int i=0;i<jsonArray.size();i++){
				temp = jsonArray.getJSONObject(i);
				modelTemp = new CarModel();
				modelTemp.setId(temp.getString("id"));
				modelTemp.setExhaustVolume(temp.getString("exhaustVolume"));
				modelTemp.setSeriesId(seriesId);
				modelTemp.setTransmission(temp.getString("transmission"));
				modelTemp.setYears(temp.getString("years"));
				carModelRepository.save(modelTemp);
				modelList.add(modelTemp);
			}
		}
		return  modelList;
	}

	@Override
	public CarModel listMaintain(String modelId) {
		CarModel carModel = carModelRepository.getOne(modelId);
		if(!carModel.getRequestJuhe()){
			String sae="";
			String api="";
			String url="http://api2.juheapi.com/carzone/maintain/cycle?key="+key+"&mid="+modelId+"&pageNumber=0&pageSizes=100";
			String charset ="UTF-8";
			String jsonResult="";
			jsonResult = CarBrandInitService.get(url, charset);//得到JSON字符串
			JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(jsonResult);
			JSONArray jsonArray =  jsonObject.getJSONArray("result");
			long maxMileague = 0;
			long tempMileague = 0;
			JSONObject jsonTempObject = null;
			for(int i=0;i<jsonArray.size();i++){
				if(jsonArray.getJSONObject(i).getString("projectId").equals("XM005")){
					jsonTempObject = jsonArray.getJSONObject(i);
					tempMileague = jsonTempObject.getLong("mileague");
					if(tempMileague > maxMileague){
						maxMileague = tempMileague;
					}
				}
			}
			if(maxMileague>0){
				url="http://api2.juheapi.com/carzone/maintain/acc?key="+key+"&mileague="+
						maxMileague+"&lid="+modelId;
				jsonResult = CarBrandInitService.get(url, charset);//得到JSON字符串
				jsonObject = com.alibaba.fastjson.JSON.parseObject(jsonResult);
				jsonObject =  jsonObject.getJSONObject("result");
				jsonArray = jsonObject.getJSONArray("projects");
				if(jsonArray.size()>0){
					for(int i=0;i<jsonArray.size();i++){
						if(jsonArray.getJSONObject(i).getString("projectId").equals("XM005")){
							sae = jsonArray.getJSONObject(i).getString("standard");
							api = jsonArray.getJSONObject(i).getString("grade");
						}
					}
				}
			}
			carModel.setRequestJuhe(true);
			carModel.setApi(api);
			carModel.setSae(sae);
			carModelRepository.save(carModel);
		}
		return carModel;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> listProductCatalog(CarModel carModel) {
		String api = carModel.getApi();
		String sap = carModel.getSae();
		
		List<Long> result = (List<Long>) jpaShareService.queryForHqlArg("select id from ProductCatalog where name=?",  sap);
		if(null!=api&&!"".equals(api)){
			List<Long> resultTwo = (List<Long>) jpaShareService.queryForHqlArg("select id from ProductCatalog where name=?", api);
			result.addAll(resultTwo);
		}
		return result;
	}

}
