package com.agilemaster.findoil.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.agilemaster.findoil.util.DateUtil;
import com.agilemaster.findoil.util.MD5Util;
@Service(value="smsService")
public class SmsServiceImpl implements SmsService,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8690377255273451321L;

	private Set<String> validateSet = new HashSet<String>();
	
	//sms企业id
	@Value(value = "${sms.username}")
	private String cropId="";
	//sms密码
	@Value(value = "${sms.passwd}")
	private String passwd="";
	
	@Value(value = "${sms.signature}")
	private String signature="";
	private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);
	
	@PostConstruct
	public void init(){
		Timer timer = new Timer();  
	    /* void java.util.Timer.schedule(TimerTask task, long delay) */  
	    timer.scheduleAtFixedRate(new TimerTask() {  
	        public void run() {  
	        	validateSet.clear();
	        }  
	    },60000,30*60*1000);// 
	}
	public int sendSMS(String phoneNumber, String content) {
		 return doSendSMS(phoneNumber,content,"");
	}
	public int sendSMS(String phoneNumber, String content, Date sendTime) {
	    return doSendSMS(phoneNumber,content,DateUtil.format(sendTime, "yyyyMMddhhmmss"));
		
	}

	public int sendSMS(String phoneNumber,String content, Calendar sendTime) {
		return doSendSMS(phoneNumber,content,DateUtil.format(sendTime.getTime(), "yyyyMMddhhmmss"));
		
	}
	private int doSendSMS(String phoneNumber, String content, String sendTime){
		 content =content + signature;
		String md5  = MD5Util.MD5(phoneNumber+content);
		if(validateSet.contains(md5)){
			log.warn("send sms fail ,can not send same message to same phoneNumber in half an hour");
		}else{
			validateSet.add(md5);
		}
		URL url = null;
		int inputLine = 1;
		try {
			String send_content=URLEncoder.encode(content.replaceAll("<br/>", " "), "GBK");//发送内容
			url = new URL("http://inolink.com/ws/BatchSend.aspx?CorpID="+cropId+"&Pwd="+passwd+"&Mobile="+phoneNumber+"&Content="+send_content+"&Cell=&SendTime="+sendTime);
			BufferedReader in = null;
			log.info("开始发送短信手机号码为 ："+phoneNumber);
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			inputLine = new Integer(in.readLine()).intValue();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			log.info("网络异常,发送短信失败！");
			inputLine=-9;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("结束发送短信返回值：  "+inputLine);
		if(inputLine<0){
			log.error("send message failed " + inputLine +" http://www.inolink.com/ConnHttp.html");
		}
		return inputLine;
	}

	public String getCropId() {
		return cropId;
	}

	public void setCropId(String cropId) {
		this.cropId = cropId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
