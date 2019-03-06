package com.xxm.advancedemo.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2019/2/27 0027
 * <p>
 * 代理模式
 */
public class ProxyDemo {

    public static void main(String... arg) {


        final ProxyClass proxyClass = new ProxyClass();

        //静态代理
//        ProxyPersonClass proxyPersonClass = new ProxyPersonClass(proxyClass);
//        proxyPersonClass.onProxyMethod();

        //动态代理
        ProxyInterface proxyInterface = (ProxyInterface) Proxy.newProxyInstance(proxyClass.getClass().getClassLoader(), new Class[]{ProxyInterface.class},
                new ProxyInvocationHandler<>(proxyClass));
        proxyInterface.onProxyMethod();

    }

}
