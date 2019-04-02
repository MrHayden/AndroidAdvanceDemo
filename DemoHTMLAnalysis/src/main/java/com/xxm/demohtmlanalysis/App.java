package com.xxm.demohtmlanalysis;

import android.app.Application;

import com.xxm.demoglide.proxy.GlideLoad;
import com.xxm.demoglide.proxy.ImageLoadProxyUtil;

/**
 * Created by xxm on 2019/4/2 0002
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImageLoadProxyUtil.getInstance().init(new GlideLoad());
    }
}
