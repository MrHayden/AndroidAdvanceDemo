package com.xxm.testmodel.proxy;

/**
 * Created by xxm on 2019/2/27 0027
 * 被代理类
 */
public class ProxyClass implements ProxyInterface {

    @Override
    public void onProxyMethod() {
        System.out.println("----被代理类执行了方法---");
    }

    @Override
    public void onProxyMethod2() {
        System.out.println("----执行了方法2--");
    }
}