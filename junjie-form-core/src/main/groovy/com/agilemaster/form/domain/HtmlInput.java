package com.agilemaster.form.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.datastax.driver.mapping.EnumType;
import com.datastax.driver.mapping.annotations.Enumerated;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
@Table(keyspace = "junjie_form", name = "HtmlInput")
public class HtmlInput implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3115267940567135039L;
	/**
	 * 唯一标识
	 */
	@PartitionKey
	private String id;
	private String formId;
	
	private String saasId;
	/**
	 * input前显示的文本(required)
	 */
	private String labelBefore; 
	/**
	 * input后显示的文本(可选)
	 * labelBefor和labelAfter两者需要填其中一个。
	 */
	private String labelAfter;
	/**
	 * (可选)
	 *map类型
	 * 	  inputType为text，有属性placeholder提交。
	 */
	private Map<String,String> inputAttrs;
	
	private Map<String,String> otherInfo;
	/**
	 * input的类型(必填)
	 */
	@Enumerated(EnumType.STRING)
	private InputType inputType = InputType.text;
	/**
	 * 显示的序号(必填),前端处理
	 */
	private Integer sequence;
	/**
	 * 字段分为显示给用户，和不显示给用户。
	 * 不显示给用户时表单创建者可以对这个值进行更改。
	 */
	private Boolean showToUser = true;
	/**
	 * 列表查询时显示
	 */
	private Boolean listShow;
	/**
	 * 存储select,checkbox,radio选项数据。
	 */
	private List<String> selectValues;
	private List<String> rightAnswers;
	
	private Date dateCreated;
	private Date lastUpdated;
	
	/**
	 * input类型数据定义
	 */
	public static   enum  InputType{
		checkboxes,
	    /**
	     * 单个文件
	     */
	    file,
	    /**
	     * 多个文件
	     */
	    files,
	    hidden,
	    email,
	    website,
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
	     * 评分
	     */
	    progress,
	    /**
	     * 日期
	     */
	    date,
	    textarea,
	    html,
	    number,
	    select,
	    paragraph,
	    section_break
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
	
	public Map<String, String> getInputAttrs() {
		return inputAttrs;
	}
	public void setInputAttrs(Map<String, String> inputAttrs) {
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
	public String getSaasId() {
		return saasId;
	}
	public void setSaasId(String saasId) {
		this.saasId = saasId;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public List<String> getRightAnswers() {
		return rightAnswers;
	}
	public void setRightAnswers(List<String> rightAnswers) {
		this.rightAnswers = rightAnswers;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public List<String> getSelectValues() {
		return selectValues;
	}
	public void setSelectValues(List<String> selectValues) {
		this.selectValues = selectValues;
	}
	public Map<String, String> getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(Map<String, String> otherInfo) {
		this.otherInfo = otherInfo;
	}
	
	
}
