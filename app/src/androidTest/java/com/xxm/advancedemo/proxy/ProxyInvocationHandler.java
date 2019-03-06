package com.xxm.advancedemo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2019/2/27 0027
 */
public class ProxyInvocationHandler<T> implements InvocationHandler {

    private T mClass;

    /**
     * @param cls 被代理类实例对象
     */
    public ProxyInvocationHandler(T cls) {
        this.mClass = cls;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(mClass, args);
    }
}
