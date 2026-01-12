package com.lhb.auth.chain.handler;

import com.lhb.auth.chain.UserLoginChain;
import com.lhb.auth.pojo.DTO.LoginDTO;
import org.springframework.stereotype.Component;

/**
 * @author 86131
 * @version 1.0
 * @description
 * @date 2026/1/9 下午2:25
 */
@Component
public class UserLoginUsernameHandler implements UserLoginChain<LoginDTO> {
    @Override
    public void handler(LoginDTO requestParam) {
        String username = requestParam.getUsername();
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (username.length() < 3 || username.length() > 50) {
            throw new IllegalArgumentException("用户名长度不合法");
        }
        if (!username.matches("^[a-zA-Z0-9_.@-]+$")) {
            throw new IllegalArgumentException("用户名包含非法字符");
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
