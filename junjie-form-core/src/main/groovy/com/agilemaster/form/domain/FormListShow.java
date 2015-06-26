package com.agilemaster.form.domain;

import java.io.Serializable;
import java.util.Date;

import com.datastax.driver.mapping.annotations.UDT;

/**
 * 新定义数据类型，方便列表显示表单。
 * @author asdtiang
 * 当为saas时，startTime为表单开始回答时间，endTime为结束回答时间。
 * 当为user时，startTime为表单开始提交时间，endTime为结束提交时间。
 * 意义虽然一样，但程序实现时是不一样的。请注意。
 */
@UDT (keyspace = "junjie_form", name = "FormListShow")
public class FormListShow   implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5497177824207251105L;
	private String formId;
	private String name;
	private Date dateCreated;
	
	private Date startTime;
	private Date endTime;
	private long answerTime;
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public long getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(long answerTime) {
		this.answerTime = answerTime;
	}
}
