package com.ms_gateway.ms_gatewa.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 0. Obtener la ruta exacta de la petición
        String path = exchange.getRequest().getURI().getPath();

        // 💡 1. EXCEPCIÓN PARA SWAGGER: Si la ruta es de documentación, la dejamos pasar sin pedir token
        if (path.contains("/swagger-ui") || path.contains("/v3/api-docs")) {
            return chain.filter(exchange);
        }

        // 2. Extrae el token de la cabecera (para las peticiones reales a tu API)
        String token = exchange.getRequest().getHeaders().getFirst("Autorizado");

        // 3. Verifica si existe el token
        if (token == null || token.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // 4. Valida el token (aquí iría tu lógica JWT)
        if (!isValidToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // 5. Si es válido, deja pasar
        return chain.filter(exchange);
    }

    private boolean isValidToken(String token) {
        return true;
    }
}

