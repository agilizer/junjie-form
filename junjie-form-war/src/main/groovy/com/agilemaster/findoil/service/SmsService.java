package com.agilemaster.findoil.service;

import java.util.Calendar;
import java.util.Date;

public interface SmsService {
	/**
	 * 立即发送短信到指定手机
	 * @param phoneNumber 手机号码
	 * @param content     发送内容
	 * @return 接口返回状态码
	 *   0 	发送成功
		-1 	账号未注册
		-2 	其他错误
		-3 	密码错误
		-4 	手机号格式不对
		-5 	余额不足
		-6 	定时发送时间不是有效的时间格式
		-7 	禁止10小时以内向同一手机号发送相同短信
	 */
	public  int sendSMS(String phoneNumber,String content);
	/**
	 * 指定日期发送短信
	 * @param phoneNumber 手机号码
	 * @param content     发送内容
	 * @param sendTime    发送日期
	 * @return
	 * 接口返回状态码
	 *   0 	发送成功
		-1 	账号未注册
		-2 	其他错误
		-3 	密码错误
		-4 	手机号格式不对
		-5 	余额不足
		-6 	定时发送时间不是有效的时间格式
		-7 	禁止10小时以内向同一手机号发送相同短信
	 */
	public  int sendSMS(String phoneNumber,String content,Date sendTime);
	
	/**
	 * 指定日期发送短信
	 * @param phoneNumber 手机号码
	 * @param content     发送内容
	 * @param sendTime    发送日期
	 * @return
	 * 接口返回状态码
	 *   0 	发送成功
		-1 	账号未注册
		-2 	其他错误
		-3 	密码错误
		-4 	手机号格式不对
		-5 	余额不足
		-6 	定时发送时间不是有效的时间格式
		-7 	禁止10小时以内向同一手机号发送相同短信
	 */
	public  int sendSMS(String phoneNumber,String content,Calendar sendTime);
	
	
}
