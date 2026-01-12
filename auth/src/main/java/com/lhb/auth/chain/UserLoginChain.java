package com.lhb.auth.chain;

import com.lhb.designpattern.Enum.ChainEnum;
import com.lhb.auth.pojo.DTO.LoginDTO;
import com.lhb.designpattern.chain.AbstractChainHandler;

public interface UserLoginChain<T extends LoginDTO> extends AbstractChainHandler<LoginDTO> {
    default String mark(){return ChainEnum.USER.name();}

}
