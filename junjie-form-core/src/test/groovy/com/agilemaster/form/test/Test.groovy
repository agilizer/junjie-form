package com.agilemaster.form.test

import com.agilemaster.form.domain.HtmlForm
import com.agilemaster.form.domain.HtmlInput
import com.agilemaster.form.domain.HtmlInput.InputType
import com.alibaba.fastjson.JSON
import com.datastax.driver.core.querybuilder.QueryBuilder

HtmlForm form = new HtmlForm()
def formId = UUID.randomUUID().toString()
form.setId(formId);
form.setName("测试表单")
form.setBeforeText("表单前文本(可选)")
form.setAfterText("表单后文本(可选)")

HtmlInput input = new HtmlInput()
input.setLabelBefore("文本表单年龄ddd示例")
input.setLabelAfter("请输入年龄")
def attrs = [min:18,max:50]
input.setInputType(InputType.number)
input.setInputAttrs(attrs)
input.setSequence(0);
def inputList = [input]

input = new HtmlInput()
input.setLabelBefore("文本表单text")
input.setLabelAfter("请输入名称")
attrs = [placeholder:"2-5个中文",maxlength:5]
input.setInputType(InputType.number)
input.setInputAttrs(attrs)
inputList.add(input)
input.setSequence(5);

form.setHtmlInputs(inputList)
println JSON.toJSONString(form)


