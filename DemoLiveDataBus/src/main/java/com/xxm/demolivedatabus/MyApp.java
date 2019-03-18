package com.xxm.demolivedatabus;

import android.app.Application;

import com.xxm.demoglide.proxy.GlideLoad;
import com.xxm.demoglide.proxy.ImageLoadProxyUtil;
import com.xxm.toolhttp.retrofit.utils.Utils;

/**
 * Created by xxm on 2019/2/26 0026
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        //初始化图片加载框架
        ImageLoadProxyUtil.getInstance().init(new GlideLoad());
    }
}
