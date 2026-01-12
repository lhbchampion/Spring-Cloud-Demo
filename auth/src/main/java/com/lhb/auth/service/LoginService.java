package com.lhb.auth.service;

import com.lhb.auth.pojo.DTO.LoginDTO;
import com.lhb.auth.pojo.VO.LoginResultVO;

import javax.security.auth.login.LoginException;

public interface LoginService {
    LoginResultVO login(LoginDTO loginDTO) throws LoginException;
}
