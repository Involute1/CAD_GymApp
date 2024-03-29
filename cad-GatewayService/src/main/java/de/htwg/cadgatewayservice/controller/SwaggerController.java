package de.htwg.cadgatewayservice.controller;

import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class SwaggerController {

    @GetMapping("/v3/api-docs/swagger-config")
    public Map<String, Object> swaggerConfig(ServerHttpRequest serverHttpRequest) throws URISyntaxException {
        String gymServiceUrl = System.getenv("GYM_SERVICE_URL");
        String userServiceUrl = System.getenv("USER_SERVICE_URL");
        String workoutServiceUrl = System.getenv("WORKOUT_SERVICE_URL");
        String reportingServiceUrl = System.getenv("REPORTING_SERVICE_URL");
        String gatewayServiceUrl = System.getenv("GATEWAY_SERVICE_URL");
        URI uri = serverHttpRequest.getURI();
        String url = new URI(uri.getScheme(), uri.getAuthority(), null, null, null).toString();
        Map<String, Object> swaggerConfig = new LinkedHashMap<>();
        List<AbstractSwaggerUiConfigProperties.SwaggerUrl> swaggerUrls = new LinkedList<>();
        if (reportingServiceUrl != null) {
            //swaggerUrls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("Gateway Service", /*gatewayServiceUrl*/+ "/v3/api-docs", "Gateway Service"));
            swaggerUrls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("Gym Service", gymServiceUrl + "/gym/v3/api-docs", "Gym Service"));
            swaggerUrls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("User Service", userServiceUrl + "/user/v3/api-docs", "User Service"));
            swaggerUrls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("Workout Service", workoutServiceUrl + "/workout/v3/api-docs", "Workout Service"));
            swaggerUrls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("Reporting Service", reportingServiceUrl + "/reporting/v3/api-docs", "Reporting Service"));
        } else {
            //swaggerUrls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("Gateway Service", url + "/v3/api-docs", "Gateway Service"));
            swaggerUrls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("Gym Service", url.replace("8080", "7081") + "/gym/v3/api-docs", "Gym Service"));
            swaggerUrls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("User Service", url.replace("8080", "7082") + "/user/v3/api-docs", "User Service"));
            swaggerUrls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("Workout Service", url.replace("8080", "7083") + "/workout/v3/api-docs", "Workout Service"));
            swaggerUrls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("Reporting Service", url.replace("8080", "7084") + "/reporting/v3/api-docs", "Reporting Service"));
        }
        swaggerConfig.put("urls", swaggerUrls);
        return swaggerConfig;
    }
}
