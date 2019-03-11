package com.xxm.testmodel.pattern.behaviour.state;

/**
 * Created by xxm on 2019/3/11 0011
 */
public class StateUtil {

    private State state;


    public void setState(State state) {
        this.state = state;
    }

    public void isStateA() {
        System.out.println("\n有事要离开,就不去看电影了\n");
        setState(new ConcreteStateA());
    }

    public void isStateB() {
        setState(new ConcreteStateB());
    }

    public void postStateA() {
        state.onStateA();
    }

    public void postStateB() {
        state.onStateB();
    }
}
