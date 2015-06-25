package com.agilemaster.findoil.bootstrap

import org.springframework.beans.BeansException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

import com.agilemaster.findoil.repository.CarBrandRepository
import com.agilemaster.findoil.repository.CarSeriesRepository
import com.agilemaster.findoil.util.PinYin2Abbreviation
import com.agilemaster.share.service.JpaShareService
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject

@Service
class CarBrandInitService implements ApplicationContextAware {

	@Autowired
	CarBrandRepository carBrandRepository;
	@Autowired
	CarSeriesRepository carSeriesRepository;
	@Autowired
	JpaShareService jpaShareService;
	ApplicationContext applicationContext;
	

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
	throws BeansException {
		this.applicationContext =  applicationContext;
	}

	void init(){
		if (carBrandRepository.count() > 0){
			return;
		}
		Resource template = applicationContext.getResource("classpath:car-brand.json");
		JSONObject jsonObject = com.alibaba.fastjson.JSON.parseObject(template.getFile().getText())
		JSONArray array = jsonObject.getJSONArray("result")
		StringBuffer sql=new StringBuffer("insert  into `car_brand`(`brand_id`,`brand_name`,`brand_letter`,`image_src`) "+
		"values ('10000','通用','ALL','')")
		def brandIdList = []
		String brandLetter = ""
		array.each {carBrand->
			brandLetter = PinYin2Abbreviation.cn2py(carBrand.brandName).charAt(0).toString().toUpperCase()
			sql.append(",('${carBrand.brandId}','${carBrand.brandName}','${brandLetter}','${carBrand.imageSrc}')");
			brandIdList.add(carBrand.brandId)
		}
		jpaShareService.executeNativeSql(sql.toString())
		//series
		template = applicationContext.getResource("classpath:car-series.json");
		array= com.alibaba.fastjson.JSON.parseArray(template.getFile().getText())
		sql=new StringBuffer("insert  into `car_series`(`id`,`brand_id`,`brand_name`,`car_series`,`car_series_id`) "+
			"values ('100000','10000','通用','通用','all')")
		array.each {carSeries->
			sql.append(",('${carSeries.id}','${carSeries.brandId}','${carSeries.brandName}','${carSeries.carSeries}','${carSeries.carSreiesId}')");
		}
		jpaShareService.executeNativeSql(sql.toString())
		/**
		String url="http://api2.juheapi.com/carzone/car/series/list?key=73bb3a4f422bffbf4511ccadd238e1f5&bid="
		String charset ="UTF-8";
		String jsonResult=""
		JSONArray arraySeries=[]
		brandIdList.each {brandId->
			jsonResult = get(url+brandId, charset);//得到JSON字符串
			jsonObject = com.alibaba.fastjson.JSON.parseObject(jsonResult)
			arraySeries.addAll(jsonObject.getJSONArray("result"))
		}
		def file = new File("d:/car-series.json")
		file.write(JSON.toJSONString(arraySeries))
		**/
	}


	public static String get(String urlAll,String charset){
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";//模拟浏览器
		try {
			URL url = new URL(urlAll);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(30000);
			connection.setConnectTimeout(30000);
			connection.setRequestProperty("User-agent",userAgent);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(
					is, charset));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
