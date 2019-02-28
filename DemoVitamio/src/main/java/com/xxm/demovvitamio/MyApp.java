package com.xxm.demovvitamio;

import android.app.Application;

import com.xxm.toolvitamio.Vitamio;

/**
 * Created by Administrator on 2019/2/25 0025
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Vitamio.isInitialized(this);
    }
}
