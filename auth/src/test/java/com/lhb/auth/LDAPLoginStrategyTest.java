package com.lhb.auth;

import com.lhb.auth.pojo.DTO.LoginDTO;
import com.lhb.auth.pojo.VO.LoginResultVO;
import com.lhb.auth.strategy.LDAPLoginStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LDAPLoginStrategyTest {

    @Autowired
    private LDAPLoginStrategy ldapLoginStrategy;

    @Test
    void testLdapUserLogin() {
        LoginDTO dto = new LoginDTO();
        dto.setUsername("ldap_user_1");
        dto.setPassword("123456");

        LoginResultVO result = ldapLoginStrategy.login(dto);
        System.out.println(result);

        assertNotNull(result);
        assertEquals("ldap_user_1", result.getUsername());
        assertNotNull(result.getAccessToken());
        assertEquals("LDAP", result.getLoginType());
    }

    @Test
    void testLdapEditorLogin() {
        LoginDTO dto = new LoginDTO();
        dto.setUsername("ldap_editor_1");
        dto.setPassword("123456");

        LoginResultVO result = ldapLoginStrategy.login(dto);
        System.out.println(result);

        assertNotNull(result);
        assertEquals("ldap_editor_1", result.getUsername());
        assertNotNull(result.getAccessToken());
        assertEquals("LDAP", result.getLoginType());
    }

    @Test
    void testLdapAdminLogin() {
        LoginDTO dto = new LoginDTO();
        dto.setUsername("ldap_adm_1");
        dto.setPassword("123456");

        LoginResultVO result = ldapLoginStrategy.login(dto);
        System.out.println(result);

        assertNotNull(result);
        assertEquals("ldap_adm_1", result.getUsername());
        assertNotNull(result.getAccessToken());
        assertEquals("LDAP", result.getLoginType());
    }

    @Test
    void testInvalidLogin() {
        LoginDTO dto = new LoginDTO();
        dto.setUsername("ldap_user_1");
        dto.setPassword("wrongpassword");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ldapLoginStrategy.login(dto);
        });

        System.out.println(exception.getMessage());
        assertTrue(exception.getMessage().contains("LDAP 用户名或密码错误"));
    }
}
