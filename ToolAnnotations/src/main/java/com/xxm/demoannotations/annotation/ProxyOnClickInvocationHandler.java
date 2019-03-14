package com.xxm.demoannotations.annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by xxm on 2019/3/12 0012
 * 动态代理
 */
public class ProxyOnClickInvocationHandler implements InvocationHandler {

    private Object cls;

    public ProxyOnClickInvocationHandler(Object cls) {
        this.cls = cls;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object onClick = method.invoke(cls, args);
        return onClick;
    }
}
