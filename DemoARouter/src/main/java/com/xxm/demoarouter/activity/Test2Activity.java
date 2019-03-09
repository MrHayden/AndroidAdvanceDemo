package com.xxm.demoarouter.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xxm.demoarouter.R;
import com.xxm.demoarouter.base.BeanTest;
import com.xxm.demoarouter.base.Constance;
import com.xxm.demoarouter.bean.BeanUser;

/**
 * Created by xxm on 2019/2/28 0028
 */
@Route(path = Constance.PATH_TEST2)
public class Test2Activity extends AppCompatActivity {

    @Autowired
    String name;

    @Autowired
    int age;

    @Autowired
    BeanTest beanTest;

    @Autowired
    BeanUser beanUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test2);
        ARouter.getInstance().inject(this);

        ((TextView) findViewById(R.id.textview1)).setText("收到的数据name：" + name + "   age:" + age + "岁" + "--" + beanUser.toString() + "--" + beanTest);
    }
}
