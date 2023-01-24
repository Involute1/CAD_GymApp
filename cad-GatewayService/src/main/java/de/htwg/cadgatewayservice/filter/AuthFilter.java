package de.htwg.cadgatewayservice.filter;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (exchange.getRequest().getPath().toString().contains("healthcheck")) return chain.filter(exchange);
        List<String> authHeader = exchange.getRequest().getHeaders().get("Authorization");
        if (authHeader == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        String[] split = authHeader.get(0).split(" ");
        if (split[0].equalsIgnoreCase("Basic")) {
            //TODO find way to decode this in userService
            String a = new String(Base64.decode(split[1]));
            if (!isAuthorizedEmailPw()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } else {
            String token = split[1];
            if (!isAuthorizedToken(token)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private boolean isAuthorizedEmailPw() {
        //TODO request to userService
        return false;
    }

    private boolean isAuthorizedToken(String token) {
        //TODO request to userService
        return false;
    }
}
