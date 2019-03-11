package com.xxm.testmodel.pattern.behaviour.responsibility;

/**
 * Created by xxm on 2019/3/11 0011
 */
public abstract class PostMan {

    public PostMan nextPostMan;

    public abstract void handlerCourier(String address);//派送快递
}
