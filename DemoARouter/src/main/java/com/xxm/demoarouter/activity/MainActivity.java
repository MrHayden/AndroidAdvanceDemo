package com.xxm.demoarouter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xxm.demoarouter.R;
import com.xxm.demoarouter.base.BeanTest;
import com.xxm.demoarouter.base.Constance;
import com.xxm.demoarouter.bean.BeanUser;
import com.xxm.demoarouter.service.SayHelloProvider;
import com.xxm.demoarouter.service.SingleProvider;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

    }

    public static Activity getThis() {
        return activity;
    }

    //  应用内简单的跳转(通过URL跳转在'进阶用法'中)并拦截
    public void onGoActivity(View view) {
        ARouter.getInstance().build(Constance.PATH_TEST1).navigation(MainActivity.this, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {

            }

            @Override
            public void onInterrupt(Postcard postcard) {
                super.onInterrupt(postcard);
                Log.e(TAG,"被拦截了");
            }
        });
    }

    //应用内携带参数的跳转(通过URL跳转在'进阶用法'中)
    public void onParametersGoActivity(View view) {
        ARouter.getInstance().build(Constance.PATH_TEST2)
                .withString("name", "老王")
                .withInt("age", 24)
                .withObject("beanTest", new BeanTest("beanTest"))
                .withParcelable("beanUser", new BeanUser(20190301, "老王", 24, 1))
                .navigation();
    }

    // ByName调用服务
    public void onByProvide(View view) {
        ((SayHelloProvider) ARouter.getInstance().build(Constance.PATH_PROVIDER_SAY_HELLO)
                .navigation()).sayHello("老王");
    }

    //调用单类
    public void onCallSingle(View view) {
        ARouter.getInstance().navigation(SingleProvider.class).saySingleHello("单例老王");
    }

    //通过url跳转
    public void onGoWebAct(View view) {
        ARouter.getInstance().build(Constance.PATH_WEB_ACT)
                .withString("url", "file:///android_asset/schame-test.html")
                .navigation();
    }

}
