package com.agilemaster.form.war;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.agilemaster.form.war.service.ShareService;
import com.fasterxml.classmate.TypeResolver;
import static springfox.documentation.schema.AlternateTypeRules.*;

@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = { "com.agilemaster.form.war" })
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
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"classpath:/static/resources/");
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

	@Bean
	@DependsOn("cassandraTemplate")
	public FilterRegistrationBean myFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new CorsFilter());
		Collection<String> urlPatterns = new HashSet<String>();
		urlPatterns.add("/api/*");
		registration.setUrlPatterns(urlPatterns);
		return registration;
	}

	@Bean
	public Docket petApi() {
		List<ApiKey> apiKeys = new ArrayList<ApiKey>();
		apiKeys.add(new ApiKey("saasKeyaaa", "saasKey", "header"));
		apiKeys.add(new ApiKey("accessKeya", "accessKey", "header"));

		List<SecurityContext> securityConList = new ArrayList<SecurityContext>();
		securityConList.add(securityContext());
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class)
				.alternateTypeRules(
						newRule(typeResolver.resolve(DeferredResult.class,
								typeResolver.resolve(ResponseEntity.class,
										WildcardType.class)), typeResolver
								.resolve(WildcardType.class)))
				.useDefaultResponseMessages(false).securitySchemes(apiKeys)
				.securityContexts(securityConList).enableUrlTemplating(true);
	}

	private SecurityContext securityContext() {
		AuthorizationScope authorizationScope = new AuthorizationScope(
				"global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		List<SecurityReference> securityRef = new ArrayList<SecurityReference>();
		securityRef
				.add(new SecurityReference("saasKeyaaa", authorizationScopes));
		securityRef
				.add(new SecurityReference("accessKeya", authorizationScopes));
		return SecurityContext.builder().securityReferences(securityRef)
				.forPaths(PathSelectors.regex("/api/v1/*")).build();
	}

	@Autowired
	private TypeResolver typeResolver;

}
