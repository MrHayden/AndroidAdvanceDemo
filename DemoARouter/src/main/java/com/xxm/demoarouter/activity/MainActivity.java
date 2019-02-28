package com.xxm.demoarouter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xxm.demoarouter.R;
import com.xxm.demoarouter.base.Constance;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 应用内简单的跳转(通过URL跳转在'进阶用法'中)
                ARouter.getInstance().build(Constance.PATH_TEST1).navigation();
            }
        });

        findViewById(R.id.button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 应用内简单的跳转(通过URL跳转在'进阶用法'中)
                ARouter.getInstance().build(Constance.PATH_TEST2)
                        .withString("name", "老王")
                        .withInt("age", 24)
                        .navigation();
            }
        });
    }
}
