package com.agilemaster.form.test.options.counter;

import static org.junit.Assert.assertEquals

import org.junit.Test

import com.agilemaster.form.domain.FormSaas
import com.agilemaster.form.domain.HtmlForm
import com.agilemaster.form.option.HtmlFormOptionsImpl
import com.agilemaster.form.option.HtmlFormOptions
import com.agilemaster.form.option.counter.FormSaasCounterOptions
import com.agilemaster.form.option.counter.SaasCounterOptions
import com.agilemaster.form.test.BaseTest

public class FormSaasCounterOptionsTest extends BaseTest{
	
	@Test
	public void testInc(){
		FormSaasCounterOptions counter = new FormSaasCounterOptions();
		FormSaas formSaas = createFormSaas();
		HtmlForm htmlForm = new HtmlForm();
		htmlForm.setSaasId(formSaas.getId())
		htmlForm = htmlFormOptions.save(htmlForm);
		counter.inc(formSaas.getId())
		assertEquals(1,counter.getCount(htmlForm.getSaasId()));
		
		// saasCounter test
		SaasCounterOptions saasCounterOptions = new SaasCounterOptions();
		assertEquals(1,saasCounterOptions.getCount());
	}
}
