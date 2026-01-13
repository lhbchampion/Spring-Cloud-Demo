package com.lhb.auth.config;

import com.lhb.auth.mapper.GitHubUserMapper;
import com.lhb.auth.pojo.DO.GitHubUserDO;
import com.lhb.base.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    final
    GitHubUserMapper gitHubUserMapper;
    final
    JwtUtil jwtUtil;

    public SecurityConfig(GitHubUserMapper gitHubUserMapper, JwtUtil jwtUtil) {
        this.gitHubUserMapper = gitHubUserMapper;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 不需要 CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/error","/login").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler((request, response, authentication) -> {

                            OAuth2User principal = (OAuth2User) authentication.getPrincipal();

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

                            String token = jwtUtil.generateToken(
                                    user.getId().toString(),
                                    buildClaims(user.getRole())
                            );

                            // ⭐ 核心：重定向到前端
                            response.sendRedirect(
                                    "http://localhost:8081/login-success?token=" + token
                            );
                        })
                );

        return http.build();
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
