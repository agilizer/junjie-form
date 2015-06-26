package com.agilemaster.form;

import static org.junit.Assert.fail;
import org.junit.Test;

import com.agilemaster.form.service.FormOptions;
import com.agilemaster.form.service.FormOptionsImpl;

public class FormOptionsTest {
	FormOptions formOptions = new FormOptionsImpl();
	@Test
	public void test() {
		formOptions.init("127.0.0.1");
	}

}
