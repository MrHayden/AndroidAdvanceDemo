package com.xxm.testmodel.pattern.behaviour.strategy;

/**
 * Created by xxm on 2019/3/11 0011
 * 策略类A
 */
public class StrategyA implements Strategy {

    @Override
    public void onStrategy() {
        System.out.println("这是策略方法A");
    }
}
