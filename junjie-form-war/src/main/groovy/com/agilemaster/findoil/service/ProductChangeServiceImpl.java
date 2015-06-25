package com.agilemaster.findoil.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.Product;
import com.agilemaster.findoil.domain.ProductPriceChange;
import com.agilemaster.findoil.domain.ProductPriceChange.ChangeStatus;
import com.agilemaster.findoil.repository.ProductPriceChangeRepository;
import com.agilemaster.findoil.repository.ProductRepository;
import com.agilemaster.findoil.util.StaticMethod;
import com.agilemaster.share.service.JpaShareService;
import com.agilemaster.share.service.JdbcPage;

@Service
public class ProductChangeServiceImpl implements ProductChangeService{
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductPriceChangeRepository productPriceChangeRepository;
	@Autowired
	UserService userService;
	@Autowired
	JpaShareService jpaShareService;
	@Override
	public Map<String, Object> submitChangePrice(Long productId, Float changePrice) {
		Map<String,Object> result = StaticMethod.genResult();
		Product product = productRepository.findOne(productId);
		if(product!=null){
			ProductPriceChange productPriceChange = new ProductPriceChange();
			productPriceChange.setAuthor(userService.currentUser());
			productPriceChange.setProduct(product);
			productPriceChange.setSourcePrice(product.getUnitPrice());
			productPriceChange.setChangeStatus(ChangeStatus.SUBMIT);
			productPriceChange.setTargetPrice(product.getUnitPrice()+changePrice);
			productPriceChange.setChangePrice(changePrice);
			productPriceChange.setSubmitDate(Calendar.getInstance());
			productPriceChangeRepository.save(productPriceChange);
			product.setCurrentPriceChange(productPriceChange);
			productRepository.save(product);
			result.put(FindOilConstants.SUCCESS, true);
		}else{
			result.put(FindOilConstants.ERROR_MSG, "没有找到相关数据 ！请检查参数");
		}
		return result;
	}
	@Override
	public Map<String, Object> checkChangePrice(Long id, Boolean result) {
		Map<String,Object> dealResult = StaticMethod.genResult();
		ProductPriceChange productPriceChange = productPriceChangeRepository.findOne(id);
		if(productPriceChange!=null){
			if(result){
				productPriceChange.setAccessDate(Calendar.getInstance());
				productPriceChange.setAccessUser(userService.currentUser());
				productPriceChange.setChangeStatus(ChangeStatus.ACCESS);
				Product product = productPriceChange.getProduct();
				product.setUnitPrice(productPriceChange.getTargetPrice());
				product.setLatestChangePrice(productPriceChange.getChangePrice());
				productRepository.save(product);
			}else{
				productPriceChange.setAccessDate(Calendar.getInstance());
				productPriceChange.setAccessUser(userService.currentUser());
				productPriceChange.setChangeStatus(ChangeStatus.FAIL);
			}
			productPriceChangeRepository.save(productPriceChange);
			dealResult.put(FindOilConstants.SUCCESS, true);
		}else{
			dealResult.put(FindOilConstants.ERROR_MSG, "没有找到相关数据 ！请检查参数");
		}
		return dealResult;
	}
	@Override
	public JdbcPage checkList(int max,int offset,ChangeStatus changeStatus) {
		String hql="FROM ProductPriceChange p WHERE p.changeStatus=:changeStatus ";
		String countHql="SELECT COUNT(*) FROM ProductPriceChange  p WHERE p.changeStatus=:changeStatus";
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("changeStatus", changeStatus);
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, countHql, max, offset, parameterMap);
		return jdbcPage;
	}

}
