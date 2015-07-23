package com.agilemaster.form.war;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.agilemaster.form.war.service.ShareService;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.agilemaster.form.war"})
public class WebAppConfig extends WebMvcConfigurerAdapter {
	private static final Logger log = LoggerFactory
			.getLogger(WebAppConfig.class);
    @Autowired
	ShareService shareService;
    // Maps resources path to webapp/resources
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	/**
    	 * 设置js css文件路径
    	 */
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/resources/");
    }

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    // Provides internationalization of messages
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        return source;
    }
   
}
