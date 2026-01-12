package com.lhb.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

@Configuration
public class LdapConfig {

    @Bean
    public LdapContextSource contextSource(
            @Value("${spring.ldap.urls}") String ldapUrls,
            @Value("${spring.ldap.base}") String baseDn,
            @Value("${spring.ldap.username}") String username,
            @Value("${spring.ldap.password}") String password) {
        LdapContextSource source = new LdapContextSource();
        source.setUrl(ldapUrls);
        source.setBase(baseDn);
        source.setUserDn(username);
        source.setPassword(password);
        source.afterPropertiesSet();
        return source;
    }

    @Bean
    public LdapTemplate ldapTemplate(LdapContextSource contextSource) {
        return new LdapTemplate(contextSource);
    }
}
