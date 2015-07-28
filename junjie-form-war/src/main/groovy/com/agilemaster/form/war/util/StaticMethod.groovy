package com.agilemaster.form.war.util

import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.regex.Matcher
import java.util.regex.Pattern

import javax.imageio.ImageIO
import javax.servlet.http.HttpServletRequest

import com.agilemaster.form.constants.FormWarConstants


/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-1
 * Time: 下午9:20
 * To change this template use File | Settings | File Templates.
 */
class StaticMethod {
	public static String UnicodeToString(String str) {
		Pattern pattern = Pattern.compile("(\\\\u(\\w{4}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch.toString() + "");
		}
		return str;
	}
	public static void createDir(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs()
		}
	}

	static decimalFormat = new DecimalFormat("#.##")
	/**
	 * #.##
	 * @return
	 */
	public static String formatDouble(sourceDouble){

		return sourceDouble==null?"":decimalFormat.format(sourceDouble)
	}

	public static String getShowFileSize(long fileSize){
		String showStr = ""
		def kb = fileSize / 1024
		if (kb > 1024) {
			showStr =  decimalFormat.format(kb / 1024) + "M"
		} else {
			showStr =   decimalFormat.format(kb) +"KB"
		}
		return showStr
	}
	/**
	 * 截取长度文字（charAt在0-125的长度算1，其它包括中文算2），并转义文字编码，超过文字长度的追加...
	 * @param str
	 * @param length
	 * @return
	 */
	public static String lenECString(Object obj,int length){
		if(obj==null){
			return "";
		}
		String str=obj.toString();
		int len=0;
		int strLen=str.length();
		for(int i=0;i<strLen;i++){
			int p=str.charAt(i);
			if(p>125||p<0){
				len+=2;
			}else {
				len++;
			}
			if(length<len){
				return str.substring(0, i)+"..";
			}
		}
		return str;
	}
	static Map<String,DateFormat> dateFormatMap = new HashMap<String,DateFormat>()
	public static Date parseDate(String dateStr,String format){
		if(null==dateStr||dateStr==""){
			return null;
		}
		DateFormat fmt =  dateFormatMap.get(format)
		if(dateFormatMap.get(format)==null){
			fmt =new SimpleDateFormat(format);
			dateFormatMap.put(dateStr,format)
		}
		return fmt.parse(dateStr);
	}
	/**
	 * return yyyy-MM-dd
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr){
		return parseDate(dateStr,"yyyy-MM-dd");
	}
	/**
	 * /aa /bb  -->/aa/bb
	 * @param targetOne
	 * @param targetTwo
	 * @return not endWith /
	 */
	public static String filePathAdd(String targetOne,String targetTwo){
		if(targetOne.endsWith("/")){
			targetOne = targetOne.substring(0,targetOne.length()-1)
		}
		if(targetTwo.startsWith("/")){
			targetTwo = targetTwo.substring(1,targetOne.length())
		}
		targetOne = targetOne +"/"+ targetTwo
		if(targetOne.endsWith("/")){
			targetOne = targetOne.substring(0,targetOne.length()-1)
		}
		return  targetOne
	}
	/**
	 * 旋转图片为指定角度
	 *
	 * @param bufferedimage
	 *            目标图像
	 * @param degree
	 *            旋转角度
	 * @return
	 */
	public static File rotateImage(File file,
			final int degree) {
		if(degree!=0){
			BufferedImage bufferedimage = ImageIO.read(file);
			int w = bufferedimage.getWidth();
			int h = bufferedimage.getHeight();
			int type = bufferedimage.getColorModel().getTransparency();
			BufferedImage img;
			Graphics2D graphics2d;
			(graphics2d = (img = new BufferedImage(w, h, type))
					.createGraphics()).setRenderingHint(
					RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
			graphics2d.drawImage(bufferedimage, 0, 0, null);
			graphics2d.dispose();
			ImageIO.write(img, "jpg", file);
		}
		return file;
	}
	public static Map<String, Object> genResult(){
		def result = [:]
		result.put(FormWarConstants.SUCCESS,false)
		return  result
	}
	public static String getFileExtention(File file){
		String result = "";
		if(file.exists()){
			def fileName = file.getName()
			int dotPos = fileName.lastIndexOf(".")
			result = (dotPos) ? fileName[dotPos + 1..fileName.length() - 1] : ""
		}
		return result.toLowerCase()
	}
	public static String genGetUrl(Map<String,Object> parameterMap){
		if(parameterMap==null || parameterMap.size() <=0)return "";
		StringBuffer buffer = new StringBuffer("?")
		Iterator iter = parameterMap.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			Object value = parameterMap.get(key);
			if(value==null || "".equals(value.toString().trim()))continue;
			if(value instanceof Long[]){
				Long[] valueArray = (Long[])value;
				valueArray.each {
					buffer.append(key+"=").append(it.toString()+"&");
				}
			}else{
				buffer.append(key+"=").append(value.toString()+"&");
			}
		}
		String result = buffer.toString();
		if(result==null || result.length()==0)return "";
		return result.subSequence(0, buffer.length()-1)
	}
	public static String getClientIp(HttpServletRequest request){
		if(null==request.getHeader("x-forwarded-for")){
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
}
