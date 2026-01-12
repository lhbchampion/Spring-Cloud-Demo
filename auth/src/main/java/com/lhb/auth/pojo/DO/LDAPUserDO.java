package com.lhb.auth.pojo.DO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 86131
 * @version 1.0
 * @description LDAP用户表
 * @date 2026/1/9 下午11:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LDAPUserDO {

    private Long id;

    /** LDAP 登录名 */
    private String username;

    /** 显示名 */
    private String name;

    private String password;

    /** 邮箱 */
    private String email;


    private String role;
}
