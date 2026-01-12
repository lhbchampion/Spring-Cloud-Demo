package com.lhb.auth.config;

import com.lhb.designpattern.strategy.AbstractExecuteStrategy;
import com.lhb.designpattern.strategy.AbstractExecuteStrategyContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class StrategyContextConfiguration {

    @Bean
    public AbstractExecuteStrategyContext abstractStrategyContext(
            List<AbstractExecuteStrategy<?,?>> strategyList
    ) {
        return new AbstractExecuteStrategyContext(strategyList);
    }
}
