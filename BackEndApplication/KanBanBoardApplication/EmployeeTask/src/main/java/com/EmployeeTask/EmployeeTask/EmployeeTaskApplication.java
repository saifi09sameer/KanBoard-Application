package com.EmployeeTask.EmployeeTask;

import com.EmployeeTask.EmployeeTask.filter.ProjectFilter;
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
import javax.servlet.Filter;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class EmployeeTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeTaskApplication.class, args);
	}
	@Bean
	public FilterRegistrationBean<Filter> filterUrl() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new ProjectFilter());
		filterRegistrationBean.addUrlPatterns(
				"/api/Project/getNoOFProjects/*",
				"/api/Project/filteredEmployeeListForAssign/*",
				"/api/Task/filteredEmployeeListForAssign/*",
				"/api/Task/updateTask/*",
				"/api/Task/deleteAllDeletedTasks/*",
				"/api/Task/getAllDeletedTasks/*",
				"/api/Task/getEmployeesList/*",
				"/api/Task/removeAssignEmployee/*",
				"/api/Task/getAssignEmployeeList/*",
				"/api/Task/assignTask/*",
				"/api/Admin/getTop5Projects",
				"/api/Admin/countNumberOFProjectAndTask",
				"/api/Task/updateTaskStage/*",
				"/api/Task/deleteTask/*",
				"/api/Task/createNewTask/*",
				"/api/Project/deleteAssignEmployee/*",
				"/api/Project/getAssignEmployeeList/*",
				"/api/Project/findProjectBasedOnProjectID/*",
				"/api/Project/assignEmployeeToProjects/*",
				"/api/Project/getEmployeesList",
				"/api/Project/createProject",
				"/api/Project/deleteProject/*",
				"/api/Project/getProjects",
				"/api/Task/getAllTasks/*");
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
