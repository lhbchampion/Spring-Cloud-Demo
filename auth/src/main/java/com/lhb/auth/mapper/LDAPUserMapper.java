package com.lhb.auth.mapper;

import com.lhb.auth.pojo.DO.LDAPUserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public interface LDAPUserMapper {


    /** 根据 LDAP username 查询本地用户 */
    LDAPUserDO findByUsername(@Param("username") String username);

    /** 创建用户，返回完整对象 */
    int insert(LDAPUserDO user);

    /** 从 LDAP 创建本地用户 */
    default LDAPUserDO createFromLDAP(@Param("username") String username,String password,@Param("role")String role) {
        LDAPUserDO user = new LDAPUserDO();
        user.setUsername(username);
        user.setName(username); // 如果 LDAP 没有 cn，可以先用 username
        user.setPassword(password);
        user.setEmail(null);    // LDAP 邮箱可选，如果没有可为空
        user.setRole(role);
        insert(user);
        return user;
    }
}
