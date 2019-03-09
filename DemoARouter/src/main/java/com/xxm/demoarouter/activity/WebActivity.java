package com.xxm.demoarouter.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xxm.demoarouter.R;
import com.xxm.demoarouter.base.Constance;

/**
 * Created by xxm on 2019/3/1 0001
 */
@Route(path = Constance.PATH_WEB_ACT)
public class WebActivity extends AppCompatActivity {

    @Autowired
    String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.a_webview);
        ARouter.getInstance().inject(this);


        ((WebView) findViewById(R.id.webview)).loadUrl(url);
    }
}
