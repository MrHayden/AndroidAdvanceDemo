package com.xxm.demoarouter.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xxm.demoarouter.R;
import com.xxm.demoarouter.base.Constance;

/**
 * Created by Administrator on 2019/2/28 0028
 */
@Route(path = Constance.PATH_TEST1)
public class Test1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test1);
    }
}
