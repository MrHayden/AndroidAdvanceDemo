package com.xxm.testmodel.proxy;

/**
 * Created by xxm on 2019/2/27 0027
 * 静态代理类
 */
public class ProxyPersonClass implements ProxyInterface {

    private ProxyInterface proxyInterface;

    public ProxyPersonClass(ProxyInterface proxyInterface){
        this.proxyInterface = proxyInterface;
    }

    @Override
    public void onProxyMethod() {
        System.out.println("----代理人执行的方法---");
        proxyInterface.onProxyMethod();
        System.out.println("----代理人执行的方法111---");
    }

    @Override
    public void onProxyMethod2() {
        proxyInterface.onProxyMethod2();
    }
}