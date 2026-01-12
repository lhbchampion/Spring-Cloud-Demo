package com.lhb.auth.service.impl;

import com.lhb.auth.pojo.DTO.LoginDTO;
import com.lhb.auth.pojo.VO.LoginResultVO;
import com.lhb.auth.service.LoginService;
import com.lhb.designpattern.chain.AbstractChainContext;
import com.lhb.designpattern.strategy.AbstractExecuteStrategy;
import com.lhb.designpattern.strategy.AbstractExecuteStrategyContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;

/**
 * @author 86131
 * @version 1.0
 * @description
 * @date 2026/1/8 下午8:49
 */
@Service
public class LoginServiceImpl implements LoginService {
    final
    AbstractChainContext abstractChainContext;
    final
    AbstractExecuteStrategyContext abstractExecuteStrategyContext;

    public LoginServiceImpl(AbstractChainContext abstractChainContext, AbstractExecuteStrategyContext abstractExecuteStrategyContext) {
        this.abstractChainContext = abstractChainContext;
        this.abstractExecuteStrategyContext = abstractExecuteStrategyContext;
    }

    @Override
    public LoginResultVO login(LoginDTO loginDTO) throws LoginException {
        abstractChainContext.handler(loginDTO.getLoginType(),loginDTO);

        AbstractExecuteStrategy strategyBean = abstractExecuteStrategyContext.getStrategyBean(loginDTO.getLoginType());


        return (LoginResultVO) strategyBean.executeResp(loginDTO);
    }
}
