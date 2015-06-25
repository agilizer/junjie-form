package com.agilemaster.asdtiang.study

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import com.agilemaster.findoil.FindOilApplication;

@RunWith(SpringJUnit4ClassRunner)
@SpringApplicationConfiguration(classes = FindOilApplication)
@WebAppConfiguration
class GroovyAndJavaApplicationTests {

	@Test
	void contextLoads() {
	}

}
