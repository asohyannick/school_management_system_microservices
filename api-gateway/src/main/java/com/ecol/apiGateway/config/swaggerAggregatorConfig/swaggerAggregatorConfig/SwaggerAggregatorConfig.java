package com.ecol.apiGateway.config.swaggerAggregatorConfig.swaggerAggregatorConfig;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties.SwaggerUrl;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

/**
 * Builds one docs route per service listed under springdoc.swagger-ui.urls:
 *   GET /v3/api-docs/{service-id}  ->  lb://{service-id}/v3/api-docs
 */
@Configuration
public class SwaggerAggregatorConfig {

    private static final String DOCS_PREFIX = "/v3/api-docs/";

    @Bean
    public RouterFunction<ServerResponse> apiDocsRoutes(SwaggerUiConfigProperties swaggerUiProperties) {
        return swaggerUiProperties.getUrls().stream()
                .map(SwaggerUrl::getUrl)
                .filter(url -> url.startsWith(DOCS_PREFIX))
                .map(url -> url.substring(DOCS_PREFIX.length()))
                .map(this::docsRoute)
                .reduce(RouterFunction::and)
                .orElseThrow(() -> new IllegalStateException(
                        "No services configured under springdoc.swagger-ui.urls"));
    }

    private RouterFunction<ServerResponse> docsRoute(String serviceId) {
        return route(serviceId + "-docs")
                .GET(DOCS_PREFIX + serviceId, http())
                .before(setPath("/v3/api-docs"))
                .filter(lb(serviceId))
                .build();
    }
}