package com.xxm.demohtmlanalysis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by xxm on 2019/4/2 0002
 */
public class BaseActivity extends AppCompatActivity {

    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initViewId());

        mContext = this;

        initView();
        initData();
    }

    public int initViewId() {
        return 0;
    }

    public void initView() {
    }

    public void initData() {
    }

    public void goActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

}
