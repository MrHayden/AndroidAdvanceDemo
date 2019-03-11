package com.xxm.testmodel.pattern.behaviour.state;

/**
 * Created by xxm on 2019/3/11 0011
 */
public class ConcreteStateB implements State {

    @Override
    public void onStateA() {
        System.out.println("去吃饭");
    }

    @Override
    public void onStateB() {
        System.out.println("去看电影");
    }
}
