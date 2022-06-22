package com.vitalikasaty.spring.security.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//Обращение к пользователям из БД
		auth.jdbcAuthentication().dataSource(dataSource);
		
		// Ручное прописывание пользователей и ролей		
//		UserBuilder userBuilder = User.withDefaultPasswordEncoder();
//		auth.inMemoryAuthentication()
//		.withUser(userBuilder.username("vitali")
//				.password("vitali")
//				.roles("EMPLOYEE"))
//		.withUser(userBuilder.username("elena")
//				.password("elena")
//				.roles("HR"))
//		.withUser(userBuilder.username("ivan")
//				.password("ivan")
//				.roles("HR", "MANAGER"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").hasAnyRole("EMPLOYEE", "HR", "MANAGER")
			.antMatchers("/hr_info").hasRole("HR")
			.antMatchers("/manager_info").hasRole("MANAGER")
			.and().formLogin().permitAll();
		//.antMatchers("/manager_info/**") - ** означает любой адрес начинающийся на /manager_info/
		
	}
	

}
