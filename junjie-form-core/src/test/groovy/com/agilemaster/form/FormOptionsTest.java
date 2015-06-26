package com.agilemaster.form;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.service.FormOptions;
import com.agilemaster.form.service.FormOptionsImpl;

public class FormOptionsTest {
	FormOptions formOptions = new FormOptionsImpl();
	@Test
	public void testInit() {
		formOptions.init("127.0.0.1");
	}
	@Test
	public void testSave(){
		formOptions.init("127.0.0.1");
		String id = UUID.randomUUID().toString();
		formOptions.createFormSass(id);
		System.out.println(id);
		FormSaas search = formOptions.getEntity(FormSaas.class, id);
		assertEquals(id,search.getId());
	}

}
