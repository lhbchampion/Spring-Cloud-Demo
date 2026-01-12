package com.lhb.auth.pojo.DO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 86131
 * @version 1.0
 * @description 用户表数据对象
 * @date 2026/1/9 下午3:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
}
