package com.lhb.auth.controller;

import com.lhb.designpattern.Enum.StrategyEnum;
import com.lhb.auth.mapper.GitHubUserMapper;
import com.lhb.auth.pojo.DO.GitHubUserDO;
import com.lhb.auth.pojo.DTO.LoginDTO;
import com.lhb.auth.pojo.VO.LoginResultVO;
import com.lhb.auth.service.LoginService;
import com.lhb.base.jwt.JwtUtil;
import com.lhb.base.response.ApiResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.*;

/**
 * @author 86131
 * @version 1.0
 * @description
 * @date 2026/1/8 下午8:42
 */
@RestController
@RequestMapping
public class AuthController {

    final
    GitHubUserMapper gitHubUserMapper;
    final
    JwtUtil jwtUtil;
    final
    LoginService loginService;

    public AuthController(GitHubUserMapper gitHubUserMapper, JwtUtil jwtUtil, LoginService loginService) {

        this.gitHubUserMapper = gitHubUserMapper;
        this.jwtUtil = jwtUtil;
        this.loginService = loginService;
    }



    @PostMapping("/login")
    public ApiResponse<LoginResultVO> login(@RequestBody LoginDTO loginDTO) throws LoginException {
        LoginResultVO loginResultVO = loginService.login(loginDTO);
        return ApiResponse.success(loginResultVO);
    }


    /**
     * github登录成功回调地址
     * @param principal
     * @return
     */
    @GetMapping("/loginSuccess")
    public ApiResponse<LoginResultVO> loginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        Number idNum = (Number) principal.getAttribute("id");
        Long githubId = idNum.longValue();

        GitHubUserDO user = gitHubUserMapper.findByGithubId(githubId);
        if (user == null) {
            user = new GitHubUserDO();
            user.setGithubId(githubId);
            user.setUsername(principal.getAttribute("login"));
            user.setEmail(principal.getAttribute("email"));
            user.setRole("EDITOR");
            gitHubUserMapper.insert(user);
        }



        // 生成 JWT
        String token = jwtUtil.generateToken(user.getId().toString(), buildClaims(user.getRole()));

        return ApiResponse.success( LoginResultVO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .loginType(StrategyEnum.GITHUB.name())
                .accessToken(token)
                .expiresIn(jwtUtil.getExpireSeconds())
                .build());
    }


    /**
     * 生成 JWT claims，处理角色继承
     * @param role 用户角色: COMMON, EDITOR, ADMIN
     * @return claims Map
     */
    public static Map<String, Object> buildClaims(String role) {
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
