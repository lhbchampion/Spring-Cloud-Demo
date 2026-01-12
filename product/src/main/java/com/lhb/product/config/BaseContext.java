package com.lhb.product.config;

import java.util.List;

public class BaseContext {
    private static final ThreadLocal<Long> userIdThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<List<String>> rolesThreadLocal = new ThreadLocal<>();

    // UserId
    public static void setUserId(Long userId) {
        userIdThreadLocal.set(userId);
    }
    public static Long getUserId() {
        return userIdThreadLocal.get();
    }
    public static void removeUserId() {
        userIdThreadLocal.remove();
    }

    // Roles
    public static void setRoles(List<String> roles) {
        rolesThreadLocal.set(roles);
    }
    public static List<String> getRoles() {
        return rolesThreadLocal.get();
    }
    public static void removeRoles() {
        rolesThreadLocal.remove();
    }

    // 清理所有
    public static void clear() {
        removeUserId();
        removeRoles();
    }
}
