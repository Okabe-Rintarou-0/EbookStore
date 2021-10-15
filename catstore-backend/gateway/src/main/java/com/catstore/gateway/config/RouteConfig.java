package com.catstore.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(r -> r.path("/book/**")
                        //                        .filters(f -> f.rewritePath("/book", ""))
                        .uri("lb://BOOK-SERVICE") // use Eureka register center to find service url by service name
                )
                .build();
    }
}
