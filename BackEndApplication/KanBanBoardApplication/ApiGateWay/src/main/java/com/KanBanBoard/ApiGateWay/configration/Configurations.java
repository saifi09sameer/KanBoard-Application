package com.KanBanBoard.ApiGateWay.configration;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurations {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder routeLocatorBuilder){

        return routeLocatorBuilder.routes().
                route(p->p.path("/api/Authentication/**")
                        .uri("lb://Authentication-Application"))

                .route(p->p.path("/api/Project/**",
                                "/api/Task/**",
                                "/api/Admin/**")
                        .uri("lb://EmployeeTaskApplication"))

                .route(p->p.path("/api/Notification/**")
                        .uri("lb://NotificationService"))

                .route(p->p.path("/api/Contact/**")
                        .uri("lb://ContactService"))


                .build();

    }


}