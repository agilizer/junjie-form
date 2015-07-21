package com.agilemaster.form;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilemaster.form.domain.FormSaas;
import com.agilemaster.form.service.FormOptions;
import com.agilemaster.form.service.FormOptionsImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CassandraConfig.class})
public class FormOptionsTest {
	@Autowired
	FormOptions formOptions = new FormOptionsImpl();
	@Test
	public void testSave(){
		String id = UUID.randomUUID().toString();
		formOptions.createFormSass(id);
		System.out.println(id);
		FormSaas search = formOptions.createFormSass(id);
		assertEquals(id,search.getId());
	}

}
