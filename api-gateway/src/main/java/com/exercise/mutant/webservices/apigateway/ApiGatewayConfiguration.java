package com.exercise.mutant.webservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r->r.path("/stats")
						.uri("lb://dna-checker"))
				.route(r->r.path("/mutant")
						.uri("lb://dna-checker"))
				.build();
				
	}
}
