package com.lhb.auth.strategy.base;

import com.lhb.auth.pojo.DTO.LoginDTO;
import com.lhb.auth.pojo.VO.LoginResultVO;

import javax.security.auth.login.LoginException;

/**
 * @author 86131
 * @version 1.0
 * @description
 * @date 2026/1/8 下午10:35
 */
public abstract class AbstractUserHandler {
    public abstract LoginResultVO login(LoginDTO requestParam) throws LoginException;
}
