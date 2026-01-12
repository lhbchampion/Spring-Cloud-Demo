package com.lhb.designpattern.strategy;

import javax.security.auth.login.LoginException;

public interface AbstractExecuteStrategy<REQUEST, RESPONSE> {

    /**
     * 执行策略标识
     */
     String getStrategyName();

    /**
     * 执行策略
     *
     * @param requestParam 执行策略入参
     */
    void execute(REQUEST requestParam) ;

    /**
     * 执行策略，带返回值
     *
     * @param requestParam 执行策略入参
     * @return 执行策略后返回值
     */
     RESPONSE executeResp(REQUEST requestParam) throws LoginException;
}
