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
        // 1. Extrae el token de la cabecera
        String token = exchange.getRequest().getHeaders().getFirst("Autorizado");

        // 2. Verifica si existe el token
        if (token == null || token.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // 3. Valida el token (aquí iría tu lógica JWT)
        if (!isValidToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // 4. Si es válido, deja pasar
        return chain.filter(exchange);
    }

    private boolean isValidToken(String token) {
        // TODO: Implementa validación JWT aquí
        // Opciones:
        //Llamar a un servicio de validación
        //Usar una librería JWT (io.jsonwebtoken:jjwt)
        //Validar firma y expiración
        return true; // Placeholder
    }

}

