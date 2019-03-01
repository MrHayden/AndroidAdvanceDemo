package com.xxm.demoarouter.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xxm.demoarouter.R;
import com.xxm.demoarouter.base.Constance;

/**
 * Created by Administrator on 2019/2/28 0028
 */
@Route(path = Constance.PATH_TEST1)
public class Test1Activity extends AppCompatActivity {

    @Autowired
    String extra;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test1);
        ARouter.getInstance().inject(this);
        if (!TextUtils.isEmpty(extra))
            Toast.makeText(this, "拦截后传递的内容:" + extra, Toast.LENGTH_SHORT).show();
    }
}
