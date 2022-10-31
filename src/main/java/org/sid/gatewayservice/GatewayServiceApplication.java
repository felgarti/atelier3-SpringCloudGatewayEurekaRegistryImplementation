package org.sid.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder.RouteSpec ;
import org.springframework.context.annotation.Bean;
import org.springframework.util.RouteMatcher;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
	RouteLocator routeLocator(RouteLocatorBuilder builder)
	{
		return builder.routes()
				.route("r1", r -> r.path("/customers/**")
						.uri("http://localhost:8081/"))
				.route("r2", r -> r.path("/products/**")
						.uri("http://localhost:8082/"))
				.build();
	}

}
