package com.agilemaster.findoil.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.agilemaster.findoil.ResourceNotFoundException;
import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.Invoice;
import com.agilemaster.findoil.domain.Order;
import com.agilemaster.findoil.domain.Order.OrderStatus;
import com.agilemaster.findoil.domain.OrderAddress;
import com.agilemaster.findoil.domain.Product;
import com.agilemaster.findoil.domain.Role;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.repository.OrderAddressRepository;
import com.agilemaster.findoil.repository.ProductRepository;
import com.agilemaster.findoil.service.AlipayService;
import com.agilemaster.findoil.service.InvoiceService;
import com.agilemaster.findoil.service.OrderAddressService;
import com.agilemaster.findoil.service.OrderService;
import com.agilemaster.findoil.service.ProductService;
import com.agilemaster.findoil.service.UserService;
import com.agilemaster.findoil.util.StaticMethod;
import com.agilemaster.share.service.JpaShareService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	UserService userService;
	@Autowired
	ProductService productService;
	@Autowired
	JpaShareService jpaShareService;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	OrderAddressService orderAddressService;
	@Autowired
	InvoiceService invoiceService;
	@Autowired
	AlipayService alipayService;

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handleResourceNotFoundException() {
		return "404";
	}
	
	@Secured({Role.ROLE_ADMIN,Role.ROLE_USER}) 
	@RequestMapping("/create")
	public String create(Long productId, Long orderNumber,Model model) {
		String  viewName = "";
		Order order = orderService.create(productId, orderNumber);
		if(order == null){
			model.addAttribute("error", "产品还没有上线！请等待");
			viewName = "businessError";
		}else{
			viewName = "redirect:/order/show/"+order.getId();
		}
		return viewName;
	}

	/**
	 * 管理员审核付款凭据 check_payment的订单
	 * 
	 * @param id
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/check/{id}")
	public Map<String, Object> check(@PathVariable("id") Long id, Boolean result) {
		User user = userService.currentUser();
		return orderService.check(id, result, user);
	}

	@RequestMapping("/save")
	public ModelAndView save(Long productId, Long orderssId, Long invoiceId,
			Long orderNumber, OrderAddress orderAddress, Invoice invoice,
			HttpServletRequest request) {
		// 获取当前登录买家
		User user = userService.currentUser();
		ModelAndView model = new ModelAndView();
		// 根据前台传递来的productId找到product
		Product product = productRepository.findOne(productId);
		if(product==null||(product.getSelledNumber()+orderNumber)>product.getSellSumNumber()){
			model.addObject("maxOrderNumber",  product.getSellSumNumber()-product.getSelledNumber());
			model.setViewName("order/createError");
		}else{
			// 这里的总价由orderNumber和product.price计算得出
			// parameterMap.put("sumPrice", request.getParameter("sumPrice"));
			// 生成地址对象
			if (orderssId != 0) {
				// orderAddress = new OrderAddress();
				orderAddress.setId(orderssId);
			} else {
				orderAddress.setUser(user);
			}
			if (invoiceId != 0) {
				// invoice = new Invoice();
				invoice.setId(invoiceId);

			} else {
				invoice.setUser(user);
			}
			Order order = orderService.create(product, orderNumber, orderAddress,
					invoice);
			model.addObject("order", order);
			model.addObject("product", order.getProduct());
			model.setViewName("redirect:/order/show/"+order.getId());
		}
		return model;
	}

	@RequestMapping("/show/{id}")
	public ModelAndView show(@PathVariable("id") Long id) {
		ModelAndView model = new ModelAndView();
		Order order = orderService.get(id);
		if (order != null) {
			User user = userService.currentUser();
			model.addObject("order", order);
			model.addObject("product", order.getProduct());
			model.addObject("addressList", orderAddressService.list(user));
			model.addObject("invoiceList", invoiceService.list(user));
			model.setViewName("order/show");
		} else {
			throw new ResourceNotFoundException();
		}
		return model;
	}

	/**
	 * 查询当前登录用户,所下的所有订单
	 * 
	 * @param max
	 * @param offset
	 * @return
	 */
	@RequestMapping("/user/list/{max}/{offset}")
	public ModelAndView toUserList(@PathVariable("max") Integer max,
			@PathVariable("offset") Integer offset) {
		User user = userService.currentUser();
		ModelAndView model = new ModelAndView();
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		model.addObject("paramsUrl", StaticMethod.genGetUrl(parameterMap));
		model.addObject("jdbcPage",
				orderService.list(max, offset, parameterMap, user));
		model.setViewName("order/userList");
		return model;
	}

	@ResponseBody
	@RequestMapping("/upload/paymentCredential")
	public Map<String, Object> uploadPaymentFile(MultipartFile file,
			Long orderId) {
		return orderService.uploadPaymentFile(orderId, file);
	}

	@ResponseBody
	@RequestMapping("/submitCheck")
	public Map<String, Object> submitCheck(Long orderId) {
		Map<String, Object> resultMap = StaticMethod.genResult();
		Order order = orderService.get(orderId);
		if (order != null) {
			order.setOrderStatus(OrderStatus.CHECK_PAYMENT);
			jpaShareService.save(order);
			resultMap.put(FindOilConstants.SUCCESS, true);
		}
		return resultMap;
	}
	
	@RequestMapping("/payment")
	public String payment(Long orderId,Long orderAddressId, Long invoiceId, Model model,
			String paymentType,HttpServletRequest request) {
		Order order = orderService.get(orderId);
		if (order != null) {
			OrderAddress orderAddress = orderAddressService.get(orderAddressId);
			Invoice invoice = invoiceService.get(invoiceId);
			if(invoice!=null&&orderAddress!=null){
				orderService.update(order, orderAddress, invoice);
				String result=alipayService.getPaymentHtml(order, request);
				model.addAttribute("result", result);
			}else{
				return "404";
			}
		}else{
			return "404";
		}
		return "order/paymentAlipay";
	}

	@RequestMapping("/checkPayment/{max}/{offset}")
	public String checkPayment(@PathVariable("max") Integer max,
			@PathVariable("offset") Integer offset, Model model) {
		model.addAttribute("jdbcPage",
				orderService.checkPaymentList(max, offset));
		return "order/checkPayment";
	}
}
