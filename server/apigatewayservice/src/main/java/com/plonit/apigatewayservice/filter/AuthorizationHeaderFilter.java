package com.plonit.apigatewayservice.filter;

import com.plonit.apigatewayservice.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private static final String AUTHORIZATION_HEADER = "AccessToken"; // Authorization -> AccessToken
    private final JwtTokenProvider jwtTokenProvider;

    public AuthorizationHeaderFilter(JwtTokenProvider jwtTokenProvider) {
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.debug("[게이트웨이 실행]");
            log.info("request path - {}", request.getPath());
            log.info("request uri - {}", request.getURI());

            // 1. AccessToken 유무
            if (!request.getHeaders().containsKey(AUTHORIZATION_HEADER)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            // 2. BEARER_TYPE 확인
            String token = jwtTokenProvider.resolveToken(request);
            if (token == null) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            // 3. AccessToken 유효성 확인
            try {
                if (!jwtTokenProvider.validateToken(token)) {
                    return onError(exchange, "validateToken", HttpStatus.UNAUTHORIZED);
                }
            } catch (Exception e) {
                return onError(exchange, e.getMessage(), HttpStatus.UNAUTHORIZED);
            }

            long memberKey;

            // 4. AccessToken 정보 확인
            try {
                memberKey = jwtTokenProvider.getAuthentication(token);
            } catch (Exception e) {
                return onError(exchange, e.getMessage(), HttpStatus.UNAUTHORIZED);
            }

            // 5. Refresh token - memberKey 유효성 확인
            if (!jwtTokenProvider.validateRefreshToken(memberKey)) {
                return onError(exchange, "validateRefreshToken", HttpStatus.UNAUTHORIZED);
            }

            // 6. header memberKey update
            exchange.getRequest().mutate().header("memberKey", String.valueOf(memberKey)).build();
            log.info("request path - {}", request.getPath());
            log.info("request uri - {}", request.getURI());
            log.debug("[게이트웨이 통과]");

            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(err);
        return response.setComplete();
    }
}

