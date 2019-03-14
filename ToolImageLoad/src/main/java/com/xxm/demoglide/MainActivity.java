package com.xxm.demoglide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xxm.demoglide.proxy.GlideLoad;
import com.xxm.demoglide.proxy.ImageLoadProxyUtil;
import com.xxm.demoglide.widget.WgShapeImageView;

public class MainActivity extends AppCompatActivity {

    private WgShapeImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.iv_glide);

        //在Application中初始化图片加载框架，可以选择Glide,Fresco、Picasso等。
        ImageLoadProxyUtil.getInstance().init(new GlideLoad());

        String url = "https://res.xfkjd.cn/wwj.png";

        imageView.setUrl(url);
    }
}
