package com.agilemaster.form.domain;

import java.io.Serializable;
import java.util.Map;

public class HtmlInput implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3115267940567135039L;
	/**
	 * 唯一标识
	 */
	private String id;
	
	private String saasId;
	/**
	 * input前显示的文本(可选)
	 */
	private String labelBefore; 
	/**
	 * input后显示的文本(可选)
	 * labelBefor和labelAfter两者需要填其中一个。
	 */
	private String labelAfter;
	/**
	 * (可选)
	 * json存储input的其它html属性，map类型
	 * 示例inputType为checkbox，有属性checked默认值提交。
	 * 	  inputType为text，有属性placeholder提交。
	 */
	private Map<String,Object> inputAttrs;
	/**
	 * input的类型(必填)
	 */
	private InputType inputType = InputType.text;
	/**
	 * 显示的序号(必填),前端处理
	 */
	private Integer sequence;
	/**
	 * 字段分为显示给用户，和不显示给用户。
	 * 不显示给用户时表单创建者可以对这个值进行更改。
	 */
	private Boolean showToUser;
	/**
	 * 列表查询时显示
	 */
	private Boolean listShow;
	/**
	 * 存储select,checkbox,radio选项数据。
	 */
	private Map<String,String> selectInfo;
	private ValueType valueType = ValueType.STRING;
	
	public static enum ValueType{
		DOUBLE,
		STRING,
		LONG,
		HTML
	}
	/**
	 * input类型数据定义
	 */
	public static  enum  InputType{
	    checkbox,
	    /**
	     * 单个文件
	     */
	    file,
	    /**
	     * 多个文件
	     */
	    files,
	    hidden,
	    /**
	     * 单个图像
	     */
	    image,
	    /**
	     * 多个图像
	     */
	    images,
	    password,
	    radio,
	    text,   
	    /**
	               时间
	     **/
	    dateTime,
	    /**
	     * 日期
	     */
	    date,
	    textarea,
	    html,
	    number
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLabelBefore() {
		return labelBefore;
	}
	public void setLabelBefore(String labelBefore) {
		this.labelBefore = labelBefore;
	}
	public String getLabelAfter() {
		return labelAfter;
	}
	public void setLabelAfter(String labelAfter) {
		this.labelAfter = labelAfter;
	}
	public InputType getInputType() {
		return inputType;
	}
	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}
	public Map<String, Object> getInputAttrs() {
		return inputAttrs;
	}
	public void setInputAttrs(Map<String, Object> inputAttrs) {
		this.inputAttrs = inputAttrs;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public Boolean getShowToUser() {
		return showToUser;
	}
	public void setShowToUser(Boolean showToUser) {
		this.showToUser = showToUser;
	}
	public Boolean getListShow() {
		return listShow;
	}
	public void setListShow(Boolean listShow) {
		this.listShow = listShow;
	}
	public Map<String, String> getSelectInfo() {
		return selectInfo;
	}
	public void setSelectInfo(Map<String, String> selectInfo) {
		this.selectInfo = selectInfo;
	}
	public ValueType getValueType() {
		return valueType;
	}
	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}
	public String getSaasId() {
		return saasId;
	}
	public void setSaasId(String saasId) {
		this.saasId = saasId;
	}
	
	
}
