package com.lhb.auth.config;

import com.lhb.designpattern.chain.AbstractChainContext;
import com.lhb.designpattern.chain.AbstractChainHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChainContextConfiguration {

    @Bean
    public AbstractChainContext abstractChainContext(
            List<AbstractChainHandler<?>> handlers
    ) {
        return new AbstractChainContext(handlers);
    }
}
