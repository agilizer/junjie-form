package com.agilemaster.findoil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.agilemaster.findoil.domain.Role;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("studyUserDetailsService")
	UserDetailsService userDetailsService;
	@Autowired
	@Qualifier("persistenLoginTokenRepository")
	PersistentTokenRepository tokenRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				passwordEncoder());
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/resources/**","/auth/**","/home/**"
				,"/","/test/**","/login/**","/upload/**","/product/resource/list/**",
				"/product/list/**","/product/show/**","/article/list/**","/article/show/**","/comment/**"
				,"/carBrandAndSeries/**")
		.permitAll().antMatchers("/admin/**").hasAuthority(Role.ROLE_ADMIN)
		.anyRequest().authenticated().and().formLogin()
		.loginPage("/login").permitAll().and().logout().permitAll().and().csrf().disable().rememberMe()
		.tokenRepository(tokenRepository).tokenValiditySeconds(1209600);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}
