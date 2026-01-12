package com.lhb.auth.strategy;

import com.lhb.auth.mapper.UserMapper;
import com.lhb.auth.pojo.DO.UserDO;
import com.lhb.auth.pojo.DTO.LoginDTO;
import com.lhb.designpattern.Enum.StrategyEnum;
import com.lhb.auth.pojo.VO.LoginResultVO;
import com.lhb.auth.strategy.base.AbstractUserHandler;
import com.lhb.base.jwt.JwtUtil;
import com.lhb.designpattern.strategy.AbstractExecuteStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.util.*;

/**
 * @author 86131
 * @version 1.0
 * @description 普通用户名和密码登录
 * @date 2026/1/8 下午8:05
 */
@Component
public class UserLoginStrategy extends AbstractUserHandler implements AbstractExecuteStrategy<LoginDTO,LoginResultVO> {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserMapper userMapper;
    public String getStrategyName() {
        return StrategyEnum.USER.name();
    }

    @Override
    public void execute(LoginDTO requestParam) {
    }

    @Override
    public LoginResultVO executeResp(LoginDTO requestParam) throws LoginException {
        return login(requestParam);
    }

    @Override
    public LoginResultVO login(LoginDTO requestParam) throws LoginException {

        UserDO user = userMapper.findByUsername(requestParam.getUsername());
        if (user == null ||
                !passwordEncoder.matches(requestParam.getPassword(), user.getPassword())) {
            throw new LoginException("用户名或密码错误");
        }



        // 生成 JWT
        String token = jwtUtil.generateToken(user.getId().toString(), buildClaims(user.getRole()));

        // 组装统一登录返回对象
        return LoginResultVO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .loginType(StrategyEnum.USER.name()) // 或 LoginType.PASSWORD.name()
                .accessToken(token)
                .expiresIn(jwtUtil.getExpireSeconds())
                .build();
    }

    /**
     * 生成 JWT claims，处理角色继承
     * @param role 用户角色: COMMON, EDITOR, ADMIN
     * @return claims Map
     */
    public  Map<String, Object> buildClaims(String role) {
        Set<String> roles = new HashSet<>();

        switch (role.toUpperCase()) {
            case "ADMIN":
                roles.add("COMMON");
                roles.add("EDITOR");
                roles.add("ADMIN");
                break;
            case "EDITOR":
                roles.add("COMMON");
                roles.add("EDITOR");
                break;
            case "COMMON":
                roles.add("COMMON");
                break;
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", new ArrayList<>(roles)); // 放入 JWT
        return claims;
    }
}
