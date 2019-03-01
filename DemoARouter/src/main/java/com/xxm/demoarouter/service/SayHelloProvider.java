package com.xxm.demoarouter.service;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Created by Administrator on 2019/3/1 0001
 */
public interface SayHelloProvider extends IProvider {

    void sayHello(String name);
}
