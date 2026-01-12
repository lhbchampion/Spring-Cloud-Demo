package com.lhb.auth;

import com.lhb.auth.pojo.DTO.LoginDTO;
import com.lhb.auth.pojo.VO.LoginResultVO;
import com.lhb.auth.strategy.UserLoginStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.security.auth.login.LoginException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserLoginStrategyTest {

    @Autowired
    private UserLoginStrategy userLoginStrategy;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void testUser1Login() throws LoginException {
        LoginDTO dto = new LoginDTO();
        dto.setUsername("user_1");
        dto.setPassword("user_1"); // 和你数据库密码一致

        LoginResultVO result = userLoginStrategy.login(dto);
        System.out.println(result);

        assertNotNull(result);
        assertEquals("user_1", result.getUsername());
        assertNotNull(result.getAccessToken());
        assertEquals("COMMON", result.getRole());
        assertEquals("USER", result.getLoginType());
    }

    @Test
    void testEditor1Login() throws LoginException {
        LoginDTO dto = new LoginDTO();
        dto.setUsername("editor_1");
        dto.setPassword("editor_1");

        LoginResultVO result = userLoginStrategy.login(dto);
        System.out.println(result);

        assertNotNull(result);
        assertEquals("editor_1", result.getUsername());
        assertNotNull(result.getAccessToken());
        assertEquals("EDITOR", result.getRole());
        assertEquals("USER", result.getLoginType()); // loginType 是策略名
    }

    @Test
    void testAdmin1Login() throws LoginException {
        LoginDTO dto = new LoginDTO();
        dto.setUsername("adm_1");
        dto.setPassword("adm_1");

        LoginResultVO result = userLoginStrategy.login(dto);
        System.out.println(result);

        assertNotNull(result);
        assertEquals("adm_1", result.getUsername());
        assertNotNull(result.getAccessToken());
        assertEquals("ADMIN", result.getRole());
        assertEquals("USER", result.getLoginType());
    }

    @Test
    void testInvalidUserLogin() {
        LoginDTO dto = new LoginDTO();
        dto.setUsername("user_1");
        dto.setPassword("wrongpassword");

        LoginException exception = assertThrows(LoginException.class, () -> {
            userLoginStrategy.login(dto);
        });

        System.out.println(exception.getMessage());
        assertTrue(exception.getMessage().contains("用户名或密码错误"));
    }

    @Test
    void a(){
        String[] passwords = {"123456"};
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        for (String pwd : passwords) {
            System.out.println(encoder.encode(pwd));
        }

    }
}
