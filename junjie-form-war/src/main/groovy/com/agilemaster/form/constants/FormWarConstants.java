package com.agilemaster.form.constants;


public interface FormWarConstants {
	String CASSANDRA_KEY_SPACE="zero_bar";
	String SUCCESS="success";
	String DATA="data";
	String CON_SITE_NAME="siteName";
	String CON_SITE_URL="siteUrl";
	String ERROR_MSG="errorMsg";
	String ERROR_CODE="errorCode";
	/**
	 * 短信替换
	 */
	String SMS_VAR_CHECK_CODE="##smcCode##";
	/**
	 * 注册
	 */
	String CON_SMS_REGISTER_TEMPLATE="smsRegisterTemplate";
	String SMS_REGISTER_NOTE="您好!您的注册验证码是:##smcCode##";  	
	/**
	 * 重置密码
	 */
	String CON_SMS_FINDPW_TEMPLATE="smsFindpwTemplate";
	String SMS_FINDPW_NOTE="您好!您重置密码的验证码是:##smcCode##";
	/**
	 * 存入到session时用到的key
	 */
	String SMS_CODE_SESSION_NAME = "smsCode";
	/**
	 * 验证码长度
	 */
	int DEFAULT_LENGTH = 6;
	/**
	 * 验证码随机在（0-9）里取
	 */
	String DEFAULT_CAPTCHA_CHARS = "0123456789";
	
	String CATALOG_MARKET_INFO="marketInformation";
	
	String CATALOG_STATIC_PAGE="staticPage";
	
	
}
