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
	/**
	 * form  id(必填)
	 */
	private String formId;
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
	private InputType inputType;
	/**
	 * 显示的序号(必填),前端处理
	 */
	private Integer sequence;
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
	    number
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
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
	
}
