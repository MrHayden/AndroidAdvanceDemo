package com.xxm.advancedemo;

import android.os.Bundle;
import android.widget.TextView;

import com.xxm.advancedemo.base.BaseBackActivity;

import butterknife.BindView;

public class MainActivity extends BaseBackActivity {

    @BindView(R.id.tv_test)
    TextView tv_test;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        tv_test.setText("测试");
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }
}
