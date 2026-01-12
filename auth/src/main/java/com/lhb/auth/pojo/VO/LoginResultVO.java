package com.lhb.auth.pojo.VO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResultVO {

    /**
     * 用户唯一标识
     */
    private Long userId;

    /**
     * 用户名 / 显示名
     */
    private String username;

    /**
     * 角色
     */
    private String role;

    /**
     * 登录方式
     */
    private String loginType;

    /**
     * JWT Access Token
     */
    private String accessToken;

    /**
     * Token 过期时间（秒）
     */
    private Long expiresIn;


}
