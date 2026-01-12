package com.lhb.gateway.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhb.base.response.ApiResponse;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

public class ResponseUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Mono<Void> write(ServerHttpResponse response,
                                   int code,
                                   String message) {
        try {
            ApiResponse<Object> body = ApiResponse.fail(code, message);

            byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(body);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);

            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            // ⚠️ 统一：HTTP 永远 200，业务看 code
            response.setStatusCode(org.springframework.http.HttpStatus.OK);

            return response.writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return response.setComplete();
        }
    }
}
