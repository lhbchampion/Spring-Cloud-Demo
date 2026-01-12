package com.lhb.designpattern.chain;

import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;

import java.util.*;

public final class AbstractChainContext {

    private final Map<String, List<AbstractChainHandler<?>>> container = new HashMap<>();

    public AbstractChainContext(List<AbstractChainHandler<?>> handlers) {

        if (CollectionUtils.isEmpty(handlers)) {

            return;
        }

        for (AbstractChainHandler<?> handler : handlers) {
            container
                    .computeIfAbsent(handler.mark(), k -> new ArrayList<>())
                    .add(handler);
        }

        container.values().forEach((list) ->
                list.sort(Comparator.comparing(Ordered::getOrder))
        );
    }

    @SuppressWarnings("unchecked")
    public <T> void handler(String mark, T requestParam) {
        List<AbstractChainHandler<?>> handlers = container.get(mark);
        if (CollectionUtils.isEmpty(handlers)) {
            throw new RuntimeException("Chain mark not found: " + mark);
        }

        for (AbstractChainHandler<?> handler : handlers) {
            ((AbstractChainHandler<T>) handler).handler(requestParam);
        }
    }
}
