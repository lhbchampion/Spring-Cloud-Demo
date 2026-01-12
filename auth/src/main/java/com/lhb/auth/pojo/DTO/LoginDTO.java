package com.lhb.auth.pojo.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 86131
 * @version 1.0
 * @description 登录传输对象
 * @date 2026/1/8 下午7:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {

    private String username;
    private String password;
    private String loginType;
}
