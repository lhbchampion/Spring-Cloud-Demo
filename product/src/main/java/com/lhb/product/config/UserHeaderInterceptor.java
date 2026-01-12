package com.lhb.product.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Component
public class UserHeaderInterceptor implements HandlerInterceptor {

    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_ROLES_HEADER = "X-User-Roles";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String userIdStr = request.getHeader(USER_ID_HEADER);
        String rolesHeader = request.getHeader(USER_ROLES_HEADER);

        if (userIdStr != null && !userIdStr.isEmpty() &&
            rolesHeader != null && !rolesHeader.isEmpty()) {

            try {
                Long userId = Long.valueOf(userIdStr);
                BaseContext.setUserId(userId);

                List<String> roles = Arrays.stream(rolesHeader.split(","))
                        .map(String::trim)
                        .filter(r -> !r.isEmpty())
                        .toList();
                BaseContext.setRoles(roles);

                // 打印调试
                System.out.println("ThreadLocal UserId=" + userId + ", Roles=" + roles);

                return true; // 继续执行请求
            } catch (NumberFormatException e) {
                response.setStatus(400);
                return false;
            }
        }

        // 没有 header，默认拒绝或设置 guest
        response.setStatus(401);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        BaseContext.clear(); // 请求结束清理 ThreadLocal
    }
}
