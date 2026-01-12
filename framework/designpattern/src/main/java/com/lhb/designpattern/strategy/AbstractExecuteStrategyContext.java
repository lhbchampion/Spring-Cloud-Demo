package com.lhb.designpattern.strategy;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 86131
 * @version 1.0
 * @description 获取策略工具
 * @date 2026/1/8 下午8:09
 */

public class AbstractExecuteStrategyContext {

    private final Map<String, AbstractExecuteStrategy<?,?>> strategyMap;


    public AbstractExecuteStrategyContext(List<AbstractExecuteStrategy<?,?>> strategyList) {
        strategyMap = new HashMap<>();
        for (AbstractExecuteStrategy<?,?> strategy : strategyList) {
            strategyMap.put(strategy.getStrategyName(), strategy);
        }
    }

    public AbstractExecuteStrategy<?,?> getStrategyBean(String strategy) {
        AbstractExecuteStrategy<?,?> strategyBean = strategyMap.get(strategy);
        if (strategyBean == null) {
            throw new IllegalArgumentException("不支持的策略方式: " + strategy);
        }
        return strategyBean;
    }


}
