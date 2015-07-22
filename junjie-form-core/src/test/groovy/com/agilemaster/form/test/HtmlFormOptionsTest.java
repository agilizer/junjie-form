package com.agilemaster.form.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HtmlFormOptionsTest extends BaseTest{

	@Test
	public void testCount(){
		long count = htmlFormOptions.count();
		createHtmlForm();
		assertEquals(count+1,htmlFormOptions.count());
	}
}
