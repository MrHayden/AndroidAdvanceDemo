package com.xxm.demoarouter.service;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.xxm.demoarouter.base.Constance;

/**
 * 测试单类注入
 * Created by xxm on 2019/3/1 0001
 */
@Route(path = Constance.PATH_PROVIDER_SERVICE)
public class SingleProvider implements IProvider {

    Context context;

    public void saySingleHello(String name){
        Toast.makeText(context,"sayHello:"+name,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }
}
