package com.agilemaster.findoil.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.FileInfo;
import com.agilemaster.findoil.domain.Invoice;
import com.agilemaster.findoil.domain.Order;
import com.agilemaster.findoil.domain.Product.ProductStatus;
import com.agilemaster.findoil.domain.ProductCache;
import com.agilemaster.findoil.domain.Order.OrderStatus;
import com.agilemaster.findoil.domain.OrderAddress;
import com.agilemaster.findoil.domain.Product;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.repository.FileInfoRepository;
import com.agilemaster.findoil.repository.OrderAddressRepository;
import com.agilemaster.findoil.repository.OrderRepository;
import com.agilemaster.findoil.repository.ProductRepository;
import com.agilemaster.findoil.util.StaticMethod;
import com.agilemaster.share.service.FileService;
import com.agilemaster.share.service.JpaShareService;
import com.agilemaster.share.service.JdbcPage;

@Service
public class OrderServiceImpl implements OrderService {
	private static final Logger log = LoggerFactory
			.getLogger(OrderServiceImpl.class);
	@Autowired
	ProductCacheService productCacheService;
	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderAddressRepository orderAddressRepository;

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	JpaShareService jpaShareService;
	
	@Autowired
	FileInfoRepository fileInfoRepository;
	@Autowired
	UserService userService;
	

	@Autowired
	FileService fileService;

	@Override
	public Order create(Product product,Long orderNumber,OrderAddress orderAddress,Invoice invoice) {
		User user = userService.currentUser();
		Order order = new Order();
		order.setOrderNumber(orderNumber);
		//总价格(根据orderNumber和product.unitprice 计算得出)
		order.setSumPrice(product.getUnitPrice()*order.getOrderNumber());
		order.setOrderUnitPrice(product.getUnitPrice());
		order.setProduct(product);
		order.setUser(user);
		order.setOrderStatus(OrderStatus.LOCK);
		Calendar now = Calendar.getInstance();
		//设置买家信息
		if(orderAddress.getId()==null){
			orderAddress.setDateCreated(now);
			orderAddress.setLastUpdated(now);
			jpaShareService.save(orderAddress);
		}
		if(invoice.getId()==null){
			invoice.setDateCreated(now);
			invoice.setLastUpdated(now);
			jpaShareService.save(invoice);
		}
		
		orderAddress.setUser(user);
		order.setOrderAddress(orderAddress);
		order.setDateCreated(now);
		order.setLockStartTime(now);
		now.add(Calendar.HOUR_OF_DAY, 1);
		order.setLockEndTime(now);
		order.setInvoice(invoice);
		order.setOrderStatus(OrderStatus.LOCK);
		order.setPaymentUuid(UUID.randomUUID().toString().replaceAll("-", ""));
		//TODO quartz锁定贷物1小时
		orderRepository.save(order);
		/**
		 * product lock
		 */
		product.setSelledNumber(product.getSelledNumber()+order.getOrderNumber());
		productRepository.save(product);
		return order;
	}


	@Override
	public Map<String,Object > uploadPaymentFile(Long orderId,MultipartFile file){
		Map<String,Object > resultMap = StaticMethod.genResult();
		Order order = orderRepository.findOne(orderId);
		if(order !=null){
			FileInfo fileInfo = null;
			if (!file.isEmpty()){
				try{
					fileInfo = fileService.saveFile(file, PAYMENT_FILE_DIR);	
				}catch(Exception e){
					log.error("upload file error",e);
				}
			}else{
				log.info("file is null..............");
			}
			resultMap.put("status","ok");
			resultMap.put("path",fileInfo.getStorePath());
            resultMap.put(FindOilConstants.SUCCESS,true);
			order.setPaymentCredential(fileInfo);
			//上传凭证后修改 状态位等待审核
			order.setOrderStatus(OrderStatus.CHECK_PAYMENT);
			orderRepository.save(order);
		}
		return resultMap;
	}
	
	
	@Override
	public Map<String,Object > check(Long id, Boolean result, User user) {
		Map<String,Object > resultMap = StaticMethod.genResult();
		Order order = orderRepository.findOne(id);
		if(order!=null){
			//将审核人保存入信息
			order.setAccessUser(user);
			//审核日期
			order.setCheckTime(Calendar.getInstance());
			if(result){
				//审核通过日期
				order.setOrderStatus(OrderStatus.SEND);
				order.setCanComment(true);
				order.setCheckAccessTime(Calendar.getInstance());
				productCacheService.increaseOrder(order.getProduct().getProductCache());
				//ProductCache productCache = order.getProduct().getProductCache();
				//productCache.
			}else{
				order.setOrderStatus(OrderStatus.CHECK_FAIL);
			}
			orderRepository.saveAndFlush(order);
			resultMap.put(FindOilConstants.SUCCESS, true);
		}else{
			resultMap.put(FindOilConstants.ERROR_MSG,"没有找到相关订单");
		}
		return resultMap;
	}

	@Override
	public JdbcPage list(Map<String,Object> parameterMap, User use) {
		User user = (User) parameterMap.get("user");
		String toPageNum = (String) parameterMap.get("toPageNum");
		String hql = "FROM Order o WHERE o.orderStatus=:orderStatus AND o.user.id=:userId ";
		String countHql = "SELECT COUNT(*) FROM Order  o WHERE o.orderStatus=:orderStatus AND o.user.id=:userId";
		parameterMap.clear();
		parameterMap.put("orderStatus", OrderStatus.LOCK);
		parameterMap.put("userId", user.getId());
		int offset = 1;
		if (toPageNum != null && !"".equals(toPageNum)) {
			offset = (Integer.parseInt(toPageNum) - 1) * 10;
		} else {
			offset = 0;
		}
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, countHql, 10,
				offset, parameterMap);
		return jdbcPage;
	}

	@Override
	public JdbcPage list(int max, int offset, Map<String, Object> parameterMap,
			User user) {
		parameterMap.clear();
		String hql = "FROM Order o WHERE o.user.id=:userId ";
		String countHql = "SELECT COUNT(*) FROM Order o WHERE o.user.id=:userId";
		parameterMap.put("userId", user.getId());
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, countHql, max, offset, parameterMap);
		return jdbcPage;
	}

	@Override
	public Order get(Long id) {
		return orderRepository.findOne(id);
	}

	@Override
	public JdbcPage checkPaymentList(Integer max, Integer offset) {
		String hql = "FROM Order o WHERE o.orderStatus=:orderStatus  ";
		String countHql = "SELECT COUNT(*) FROM Order  o WHERE o.orderStatus=:orderStatus ";
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("orderStatus", OrderStatus.CHECK_PAYMENT);
		JdbcPage jdbcPage = jpaShareService.queryForHql(hql, countHql, max,
				offset, parameterMap);
		return jdbcPage;
	}

	@Override
	public Order create(Long productId, Long orderNumber) {
		User user = userService.currentUser();
		Product product = productRepository.getOne(productId);
		Order order = null;
		if(product.getProductStatus()==ProductStatus.ONLINE){
			order = new Order();
			order.setOrderNumber(orderNumber);
			//总价格(根据orderNumber和product.unitprice 计算得出)
			order.setSumPrice(product.getUnitPrice()*order.getOrderNumber());
			order.setOrderUnitPrice(product.getUnitPrice());
			order.setProduct(product);
			order.setUser(user);
			order.setOrderStatus(OrderStatus.PAYMENT);
			Calendar now = Calendar.getInstance();
			order.setDateCreated(now);
			now.add(Calendar.HOUR_OF_DAY, 1);
			order.setPaymentUuid(UUID.randomUUID().toString().replaceAll("-", ""));
			//TODO quartz锁定贷物1小时
			orderRepository.save(order);
			/**
			 * product lock
			 */
			//TODO update by sql
			product.setSelledNumber(product.getSelledNumber()+order.getOrderNumber());
			productRepository.save(product);
		}
		return order;
	}

	@Override
	public void update(Order order, OrderAddress orderAddress, Invoice invoice) {
		String hql="update Order set orderAddress =? ,invoice =? where id=?";
		jpaShareService.executeForHql(hql, orderAddress,invoice,order.getId());
	}

}
