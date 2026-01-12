package com.lhb.auth.chain.handler;

import com.lhb.auth.chain.LDAPLoginChain;
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
public class LDAPLoginPasswordHandler implements LDAPLoginChain<LoginDTO> {
    @Override
    public void handler(LoginDTO requestParam) {
        String password = requestParam.getPassword();
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }
        if (password.length() < 3 || password.length() > 20) {
            throw new IllegalArgumentException("密码长度不合法");
        }
        if (password.contains(" ")) {
            throw new IllegalArgumentException("密码不能包含空格");
        }
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
