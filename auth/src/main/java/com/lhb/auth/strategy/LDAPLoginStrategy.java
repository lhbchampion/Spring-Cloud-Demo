package com.lhb.auth.strategy;

import com.lhb.auth.Enum.RoleEnum;
import com.lhb.auth.mapper.LDAPUserMapper;
import com.lhb.auth.pojo.DO.LDAPUserDO;
import com.lhb.auth.pojo.DTO.LoginDTO;
import com.lhb.designpattern.Enum.StrategyEnum;
import com.lhb.auth.pojo.VO.LoginResultVO;
import com.lhb.auth.strategy.base.AbstractLDAPLoginHandler;
import com.lhb.base.jwt.JwtUtil;
import com.lhb.designpattern.strategy.AbstractExecuteStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author 86131
 * @version 1.0
 * @description LDAP登录
 * @date 2026/1/8 下午8:08
 */
@Component
public class LDAPLoginStrategy extends AbstractLDAPLoginHandler implements AbstractExecuteStrategy<LoginDTO,LoginResultVO> {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    LDAPUserMapper ldapUserMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    LdapTemplate ldapTemplate;
    public String getStrategyName() {
        return StrategyEnum.LDAP.name();
    }

    @Override
    public void execute(LoginDTO requestParam) {

    }

    @Override
    public LoginResultVO executeResp(LoginDTO requestParam) {
        return login(requestParam);
    }

    @Override
    public LoginResultVO login(LoginDTO loginDTO) {

        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        // 1️⃣ LDAP 认证
        boolean authenticated = ldapTemplate.authenticate(
                "",
                "(uid=" + username + ")",
                password
        );

        if (!authenticated) {
            throw new RuntimeException("LDAP 用户名或密码错误");
        }

        // 2️⃣ 查找或创建本地用户
        LDAPUserDO user = ldapUserMapper.findByUsername(username);
        if (user == null) {
            // 本地创建用户时给默认角色 USER
            user = ldapUserMapper.createFromLDAP(username, passwordEncoder.encode("123456"),RoleEnum.COMMON.name());
        }


        // 生成 JWT
        String token = jwtUtil.generateToken(user.getId().toString(), buildClaims(user.getRole()));

        // 4️⃣ 返回统一登录结果
        return LoginResultVO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .role(user.getRole()) // 本地用户表里保存角色
                .loginType(StrategyEnum.LDAP.name())
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
