package com.lhb.gateway.filter;

import com.lhb.base.jwt.JwtUtil;
import com.lhb.base.response.ResultCode;
import com.lhb.gateway.config.CustomGatewayProperties;
import com.lhb.gateway.util.ResponseUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private static final String AUTH_HEADER = HttpHeaders.AUTHORIZATION;
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_ROLES_HEADER = "X-User-Roles"; // 多角色

    private final CustomGatewayProperties customGatewayProperties;

    private final JwtUtil jwtUtil;

    public AuthGlobalFilter(CustomGatewayProperties customGatewayProperties, JwtUtil jwtUtil) {
        this.customGatewayProperties = customGatewayProperties;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        AntPathMatcher pathMatcher = new AntPathMatcher();
        // 1️⃣ 正确的白名单匹配方式
        if (customGatewayProperties.getWhitelist().stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path))) {
            return chain.filter(exchange);
        }

        // 2️⃣ 读取 Authorization
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(AUTH_HEADER);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return ResponseUtil.write(
                    exchange.getResponse(),
                    ResultCode.UNAUTHORIZED,
                    "未登录"
            );
        }

        // 3️⃣ 解析 Token
        String token = authHeader.substring(BEARER_PREFIX.length());
        Claims claims;
        try {
            claims = jwtUtil.parseToken(token);
        } catch (Exception e) {
            return ResponseUtil.write(
                    exchange.getResponse(),
                    ResultCode.UNAUTHORIZED,
                    "Token 无效或已过期"
            );
        }

        // 4️⃣ 注入 userId
        String userId = claims.getSubject();

        // 5️⃣ 注入多个角色
        Object rolesObj = claims.get("roles"); // 从 JWT 中获取 roles
        String rolesHeader = "";
        if (rolesObj instanceof List<?> rolesList) {
            rolesHeader = rolesList.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
        } else if (rolesObj != null) {
            // 如果 JWT 里只有单个 role 也兼容
            rolesHeader = rolesObj.toString();
        }

        ServerHttpRequest newRequest = exchange.getRequest()
                .mutate()
                .header(USER_ID_HEADER, userId)
                .header(USER_ROLES_HEADER, rolesHeader)
                .header("X-Gateway-From", "gateway")
                .build();

        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
