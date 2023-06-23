package com.example.AuthenticationApplication;

import com.example.AuthenticationApplication.filter.EmployeeFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
public class AuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean filterUrl() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new EmployeeFilter());
		filterRegistrationBean.addUrlPatterns(
				"/api/Authentication/deleteEmployee/*",
				"/api/Authentication/getEmployeeEmailList",
				"/api/Authentication/getEmployeeDetails",
				"/api/Authentication/updateEmployee/",
				"/api/Authentication/getAllEmployeesStatus",
				"/api/Authentication/offLineStatus",
				"/api/Authentication/getAllEmployee");
		return filterRegistrationBean;
	}

//	@Bean
//	public FilterRegistrationBean filterRegistrationBean(){
//		final CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("http://localhost:4200");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**",config);
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//		return bean;
//	}

}
