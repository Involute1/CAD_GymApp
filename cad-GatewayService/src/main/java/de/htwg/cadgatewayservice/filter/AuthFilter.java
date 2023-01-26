package de.htwg.cadgatewayservice.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final String DEFAULT_USER_URL = "http://localhost:7082/user";
    private String userServiceUrl;

    public AuthFilter() {
        userServiceUrl = System.getenv("USER_SERVICE_URL");
        if (userServiceUrl == null) {
            userServiceUrl = DEFAULT_USER_URL;
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (exchange.getRequest().getPath().toString().contains("healthcheck")) return chain.filter(exchange);
        if (exchange.getRequest().getPath().toString().contains("tenant")) return chain.filter(exchange);
        if (exchange.getRequest().getPath().toString().contains("user")) return chain.filter(exchange);
        if (exchange.getRequest().getPath().toString().contains("gym")) return chain.filter(exchange);
        List<String> authHeader = exchange.getRequest().getHeaders().get("Authorization");
        if (authHeader == null || authHeader.size() == 0) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        List<String> tenantIdHeader = exchange.getRequest().getHeaders().get("TenantID");
        if (tenantIdHeader == null || tenantIdHeader.size() == 0)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        String[] authHeaderSplit = authHeader.get(0).split(" ");
        String[] tenantSplit = tenantIdHeader.get(0).split(" ");
        if (authHeaderSplit[0].equalsIgnoreCase("Token")) {
            try {
                if (!isAuthorizedToken(authHeaderSplit[1], tenantSplit[0]))
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            } catch (IOException | InterruptedException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private boolean isAuthorizedToken(String token, String tenantId) throws IOException, InterruptedException {
        if (StringUtils.isBlank(token)) return false;
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest authRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"idToken\": \"%s\", \"tenantId\": \"%s\"}".formatted(token, tenantId)))
                .uri(URI.create(userServiceUrl + "/verify"))
                .build();
        HttpResponse<String> authResponse = httpClient.send(authRequest, HttpResponse.BodyHandlers.ofString());
        return "true".equalsIgnoreCase(authResponse.body());
    }
}
