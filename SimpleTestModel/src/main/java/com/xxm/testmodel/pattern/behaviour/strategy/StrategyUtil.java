package com.xxm.testmodel.pattern.behaviour.strategy;

/**
 * Created by xxm on 2019/3/11 0011
 */
public class StrategyUtil {

    private Strategy strategy;

    public StrategyUtil(Strategy strategy) {
        this.strategy = strategy;
    }

    public void onStrategy() {
        strategy.onStrategy();
    }
}
