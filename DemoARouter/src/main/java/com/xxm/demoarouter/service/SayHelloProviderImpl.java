package com.xxm.demoarouter.service;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxm.demoarouter.base.Constance;

/**
 * Created by xxm on 2019/3/1 0001
 */
@Route(path = Constance.PATH_PROVIDER_SAY_HELLO)
public class SayHelloProviderImpl implements SayHelloProvider {

    Context mContext;

    @Override
    public void sayHello(String name) {
        Toast.makeText(mContext, "Hello " + name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
