package com.agilemaster.findoil.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期操作类
 * 
 * @author asdtiang 2011-3-21
 */
public class DateUtil {

	static Map<String, SimpleDateFormat> dateFormatMap = new HashMap<String, SimpleDateFormat>();

	/**
	 * 以指定的格式来格式化日期
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            String
	 * @return String
	 */
	public static String format(java.util.Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = dateFormatMap.get(format);
				if (dateFormatMap.get(format) == null) {
					sdf = new SimpleDateFormat(format);
					dateFormatMap.put(format, sdf);
				}
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	public static Date parse(String dateStr, String format) {
		Date result = null;
		if (dateStr != null) {
			try {
				SimpleDateFormat sdf = dateFormatMap.get(format);
				if (dateFormatMap.get(format) == null) {
					sdf = new SimpleDateFormat(format);
					dateFormatMap.put(format, sdf);
				}
				result = sdf.parse(dateStr);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	public static Calendar parseForCalendar(String dateStr, String format) {
		Calendar result = null;
		if (dateStr != null) {
			try {
				SimpleDateFormat sdf = dateFormatMap.get(format);
				if (dateFormatMap.get(format) == null) {
					sdf = new SimpleDateFormat(format);
					dateFormatMap.put(format, sdf);
				}
				Date temp = sdf.parse(dateStr);
				result = Calendar.getInstance();
				result.setTimeInMillis(temp.getTime());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	public static String format(Calendar date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = dateFormatMap.get(format);
				if (dateFormatMap.get(format) == null) {
					sdf = new SimpleDateFormat(format);
					dateFormatMap.put(format, sdf);
				}
				Date da   = new Date();
				da.setTime(date.getTimeInMillis());
				result = sdf.format(da);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 返回年份
	 * 
	 * @param date
	 *            日期
	 * @return 返回年份
	 */
	public static int getYear(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 返回年份
	 * 
	 * @param date
	 *            日期
	 * @return 返回年份
	 */
	public static int getYear(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 返回月份
	 * 
	 * @param date
	 *            日期
	 * @return 返回月份
	 */
	public static int getMonth(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 返回当前时间的月份
	 * 
	 * @return 返回当前时间的月份
	 */
	public static int getMonth() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 返回日份
	 * 
	 * @param date
	 *            日期
	 * @return 返回日份
	 */
	public static int getDay(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回小时
	 * 
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回分钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 * 
	 * @param date
	 *            日期
	 * @return 返回秒钟
	 */
	public static int getSecond(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	/**
	 * 返回毫秒表示的当前日期
	 * 
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(java.util.Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 常用的格式化日期 "yyyy-MM-dd"
	 * 
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String format(java.util.Date date) {
		return format(date, "yyyy-MM-dd");
	}

	/**
	 * 返回字符型日期yyyy/MM/dd
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型日期
	 */
	public static String getDate(java.util.Date date) {
		return format(date, "yyyy/MM/dd");
	}

	/**
	 * 返回字符型时间HH:mm:ss
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型时间
	 */
	public static String getTime(java.util.Date date) {
		return format(date, "HH:mm:ss");
	}

	/**
	 * 返回字符型日期时间yyyy/MM/dd HH:mm:ss
	 * 
	 * @param date
	 *            日期
	 * @return 返回字符型日期时间
	 */
	public static String getDateTime(java.util.Date date) {
		return format(date, "yyyy/MM/dd HH:mm:ss");
	}

	/**
	 * 日期相减
	 * 
	 * @param date
	 *            日期
	 * @param date1
	 *            日期
	 * @return 返回相减后的日期
	 */
	public static int diffDate(java.util.Date date, java.util.Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 日期相减
	 * 
	 * @param ca1
	 *            日期
	 * @param ca2
	 *            日期
	 * @return
	 */
	public static int diffDate(Calendar ca1, Calendar ca2) {
		return (int) ((ca1.getTimeInMillis() - ca2.getTimeInMillis()) / (24 * 3600 * 1000));
	}

	/**
	 * 设置日期的时，分，秒，毫秒，为零，时为HOUR_OF_DAY
	 * 
	 * @param calendar
	 * @return
	 */
	public static Calendar setZero(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	/**
	 * 测试两个日期是否为同一个月
	 * 
	 * @param date
	 * @param date1
	 * @return
	 */

	public static boolean isSameMonth(java.util.Date date, java.util.Date date1) {
		return getMonth(date) == getMonth(date1) ? true : false;
	}

	/**
	 * 测试两上日期是否为同一天
	 * 
	 * @param date
	 * @param date1
	 * @return
	 */
	public static boolean isSameDate(java.util.Date date, java.util.Date date1) {
		return getDate(date).equals(getDate(date1)) ? true : false;
	}

	/**
	 * 获取当前时间yyyy-MM-dd hh:mm:ss
	 */
	public static String getLocalTime() {
		return format(new java.util.Date(), "yyyy-MM-dd hh:mm:ss");
	}

}
