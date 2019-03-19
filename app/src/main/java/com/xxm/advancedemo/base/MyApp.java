package com.xxm.advancedemo.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.xxm.demoglide.proxy.GlideLoad;
import com.xxm.demoglide.proxy.ImageLoadProxyUtil;
import com.xxm.toolhttp.retrofit.utils.Utils;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * Created by xxm on 2019/2/28 0028
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
        BGASwipeBackHelper.init(this,null);
        //初始化图片加载框架
        ImageLoadProxyUtil.getInstance().init(new GlideLoad());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
