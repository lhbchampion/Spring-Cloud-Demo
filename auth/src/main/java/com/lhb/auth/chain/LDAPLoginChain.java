package com.lhb.auth.chain;

import com.lhb.auth.pojo.DTO.LoginDTO;
import com.lhb.designpattern.Enum.ChainEnum;
import com.lhb.designpattern.chain.AbstractChainHandler;

public interface LDAPLoginChain<T extends LoginDTO> extends AbstractChainHandler<LoginDTO> {
    default String mark(){return ChainEnum.LDAP.name();}

}
