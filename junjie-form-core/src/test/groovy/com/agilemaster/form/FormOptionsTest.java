package com.agilemaster.form;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;

import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.service.CassandraTemplate;
import com.agilemaster.form.service.CassandraTemplateDefault;
import com.agilemaster.form.service.FormOptions;
import com.agilemaster.form.service.FormOptionsImpl;

public class FormOptionsTest {
	CassandraTemplate cassandraTemplate = new CassandraTemplateDefault();
	FormOptions formOptions = new FormOptionsImpl();
	@Test
	public void testInit() {
		cassandraTemplate.init("127.0.0.1");
		cassandraTemplate.close();
	}
	@Test
	public void testSave(){
		cassandraTemplate.init("127.0.0.1");
		formOptions.setCassandraTemplate(cassandraTemplate);
		String id = UUID.randomUUID().toString();
		formOptions.createFormSass(id);
		System.out.println(id);
		FormSaas search = cassandraTemplate.getEntity(FormSaas.class, id);
		assertEquals(id,search.getId());
		cassandraTemplate.close();
	}

}
