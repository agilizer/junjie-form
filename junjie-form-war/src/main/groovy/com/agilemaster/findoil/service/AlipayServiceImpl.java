package com.agilemaster.findoil.service;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.consants.FindOilConstants;
import com.agilemaster.findoil.domain.Order;
import com.agilemaster.findoil.domain.Order.OrderStatus;
import com.agilemaster.findoil.domain.PaymentRecord;
import com.agilemaster.findoil.domain.PaymentRecord.PaymentType;
import com.agilemaster.findoil.domain.User;
import com.agilemaster.findoil.repository.OrderRepository;
import com.agilemaster.findoil.util.StaticMethod;
import com.agilemaster.share.service.JpaShareService;
import com.alibaba.fastjson.JSON;
import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
@Service
public class AlipayServiceImpl implements AlipayService{
	private static final Logger log = LoggerFactory
			.getLogger(AlipayServiceImpl.class);
    /**
     * 根据定单id生成支付页面html代码
     * @param orderId 定单id
     * @return 支付html代码
     */
   // def paymentBackService
   
    @Autowired
    ConfigDomainService configDomainService;
    @Autowired
    UserService userService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    JpaShareService jpaShareService;
    DecimalFormat df = new DecimalFormat("0.00");
    /**
     * Vector 是同步的
     */
    public static List<String> PAY_PROCESSING_LIST=new Vector<String>();
    public String getPaymentHtml(Order  order,HttpServletRequest request) {
        if(order==null){
            return "ERROR";
        }
        //必填参数//

        //请与贵网站订单系统中的唯一订单号匹配
        //传到支付宝的订单号为orderId+uuid
        User user = userService.currentUser();
        String out_trade_no = order.getId()+"-"+order.getPaymentUuid()+"-"+user.getId();
        String subject="",body="";
        //订单名称，显示在支付宝收银台里的“商品名称”里，显示在支付宝的交易管理的“商品名称”的列表里。
        subject = order.getProduct().getName()+"订单号:"+order.getId();
        //订单描述、订单详细、订单备注，显示在支付宝收银台里的“商品描述”里
        body=subject+"\n 数量："+order.getOrderNumber()+" 单价:￥"+order.getOrderUnitPrice()+" 总价:￥"+order.getSumPrice();
        //订单总金额，显示在支付宝收银台里的“应付总额”里

        //String total_fee = "0.01";
        String total_fee=df.format(order.getSumPrice());
        //total_fee="0.01";由admin用户下单,价格为0.01
        if(order.getUser().getUsername()=="11111111111"){
            total_fee="0.01";
        }
        //扩展功能参数——默认支付方式//

        //默认支付方式，取值见“即时到帐接口”技术文档中的请求参数列表
        String paymethod = "directPay";
        //默认网银代号，代号列表见“即时到帐接口”技术文档“附录”→“银行列表”
        String defaultbank = "";

        //扩展功能参数——防钓鱼//

        //防钓鱼时间戳
        String anti_phishing_key  = "";
        //获取客户端的IP地址，建议：编写获取客户端IP地址的程序
        String exter_invoke_ip= StaticMethod.getClientIp(request);
        //注意：
        //1.请慎重选择是否开启防钓鱼功能
        //2.exter_invoke_ip、anti_phishing_key一旦被设置过，那么它们就会成为必填参数
        //3.开启防钓鱼功能后，服务器、本机电脑必须支持远程XML解析，请配置好该环境。
        //4.建议使用POST方式请求数据
        //示例：
        //anti_phishing_key = AlipayService.query_timestamp();	//获取防钓鱼时间戳函数
        //exter_invoke_ip = "202.1.1.1";

        //扩展功能参数——其他///

        //自定义参数，可存放任何内容（除=、&等特殊字符外），不会显示在页面上
        String extra_common_param = ""+order.getPaymentUuid();
        //默认买家支付宝账号
        String buyer_email = "";
        //商品展示地址，要用http:// 格式的完整路径，不允许加?id=123这类自定义参数
        String serverUrl = configDomainService.getConfigString(FindOilConstants.CON_SITE_URL);
        String show_url = serverUrl + "product/show/"+order.getProduct().getId();

        //扩展功能参数——分润(若要使用，请按照注释要求的格式赋值)//

        //提成类型，该值为固定值：10，不需要修改
        //String royalty_type = "";
        //提成信息集
        //String royalty_parameters ="";
        //注意：
        //与需要结合商户网站自身情况动态获取每笔交易的各分润收款账号、各分润金额、各分润说明。最多只能设置10条
        //各分润金额的总和须小于等于total_fee
        //提成信息集格式为：收款方Email_1^金额1^备注1|收款方Email_2^金额2^备注2
        //示例：
        //royalty_type = "10"
        //royalty_parameters	= "111@126.com^0.01^分润备注一|222@126.com^0.01^分润备注二"

        //////////////////////////////////////////////////////////////////////////////////
        //服务器异步通知页面路径
        String notifyUrl = serverUrl+ "alipayBack/alipayNotify";
        //需http://格式的完整路径，不能加?id=123这类自定义参数		//页面跳转同步通知页面路径
        String returnUrl = serverUrl+ "alipayBack/alipayReturn";
        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        //sParaTemp.put("royalty_type", royalty_type);
        //sParaTemp.put("royalty_parameters", royalty_parameters);
        sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", "1");
        sParaTemp.put("notify_url", notifyUrl);
        sParaTemp.put("return_url", returnUrl);
        sParaTemp.put("seller_email", "financial_hy@chaej.com");
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", total_fee);
        sParaTemp.put("body", body);
        sParaTemp.put("show_url", show_url);
        sParaTemp.put("anti_phishing_key", anti_phishing_key);
        sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
        sParaTemp.put("extra_common_param", extra_common_param);

        //构造函数，生成请求URL
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
        return sHtmlText;

    }
    @SuppressWarnings("unused")
	public String orderPaymentBack(HttpServletRequest request,int type){
        String out_trade_no="";
		try {
			out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			log.error("error out_trade_no "+out_trade_no,e1);
		}        //获取其它参数
        if(PAY_PROCESSING_LIST.indexOf(out_trade_no)!=-1){
            //表示正在处理过支付
            return getReturn(out_trade_no,"success",type);
        }
        PAY_PROCESSING_LIST.add(out_trade_no);
        try{
            //获取支付宝POST过来反馈信息
            Map<String,String> params = new HashMap<String,String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
                params.put(name, valueStr);
            }

            String total_fee = request.getParameter("total_fee");	        //获取总金额
            String buyer_email = request.getParameter("buyer_email");		//买家支付宝账号
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            if(AlipayNotify.verify(params)){//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码

                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                    //判断该笔订单是否在商户网站中已经做过处理（可参考“集成教程”中“3.4返回数据处理”）
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                    try{
                        if(null==out_trade_no){
                            return getReturn(out_trade_no,"fail",type);
                        }
                        log.info("return params:::"+params);
                        String[] split = out_trade_no.split("-");
                        Long orderId = Long.parseLong(split[0]);
                        Order order = orderRepository.getOne(orderId);
                        //如果订单已经处理，则返回string的paymentId
                        if(null!=order&&order.getOrderStatus() == OrderStatus.FINISH){
                            //表示已经处理过支付
                            return getReturn(out_trade_no,"success",type);
                        }
                        Calendar now = Calendar.getInstance();
                        PaymentRecord paymentRecord = new PaymentRecord();
                        paymentRecord.setDateCreated(now);
                        paymentRecord.setOrder(order);
                        paymentRecord.setPaymentBackStr(JSON.toJSONString(params));
                        paymentRecord.setPaymentIp(params.get("exter_invoke_ip"));
                        paymentRecord.setPaymentType(PaymentType.ALIPAY);
                        paymentRecord.setSalesReturn(false);
                        jpaShareService.save(paymentRecord);
                        String orderUpdateHql = "update Order set paymentEndTime=?,orderStatus=?,paymentRecord=? where id=?";
                        jpaShareService.executeForHql(orderUpdateHql, now,OrderStatus.PAYMENT_FINISH,order.getId());
                        return getReturn(out_trade_no,"success",type);
                    }
                    catch(Exception e){
                        log.error(JSON.toJSONString(params),e);
                        return getReturn(out_trade_no,"success",type);
                    }
                    //请不要修改或删除
                } else {
                    log.error("execute alipay back error,trade_status ${trade_status}"+JSON.toJSONString(params));
                    return getReturn(out_trade_no,"fail",type);	//请不要修改或删除
                }
                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                //////////////////////////////////////////////////////////////////////////////////////////
            }else{//验证失败
                return getReturn(out_trade_no,"fail",type);
            }
        }
        catch(Exception e){
            log.error("",e);
            return getReturn(out_trade_no,"fail",type);
        }
        finally{
            PAY_PROCESSING_LIST.remove(out_trade_no);
        }

    }
    private String getReturn(String order_no,String result,int type){
        if(type==TYPE_NOTIFY){
            return result;
        }
        else{
            return order_no;
        }
    }

}
