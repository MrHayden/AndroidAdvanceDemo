package com.xxm.demoarouter.Interceptor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.xxm.demoarouter.activity.MainActivity;
import com.xxm.demoarouter.base.Constance;
import com.xxm.demoarouter.base.MainLooper;

/**
 * Created by Administrator on 2019/3/1 0001
 * // 比较经典的应用就是在跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查
 * // 拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行
 */
@Interceptor(priority = 8, name = "测试拦截器")
public class MyInterceptor implements IInterceptor {
    @Override
    public void process(final Postcard postcard, final InterceptorCallback callback) {

//          ...
//        callback.onContinue(postcard);  // 处理完成，交还控制权
        // callback.onInterrupt(new RuntimeException("我觉得有点异常"));      // 觉得有问题，中断路由流程
        // 以上两种至少需要调用其中一种，否则不会继续路由

        if (Constance.PATH_TEST1.equals(postcard.getPath())) {
            final AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.getThis());
            ab.setCancelable(false);
            ab.setTitle("温馨提醒");
            ab.setMessage("想要跳转到Test1Activity么？(触发了\"/inter/test1\"拦截器，拦截了本次跳转)");
            ab.setNegativeButton("继续", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    callback.onContinue(postcard);
                }
            });
            ab.setNeutralButton("算了", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    callback.onInterrupt(null);
                }
            });
            ab.setPositiveButton("加点料", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    postcard.withString("extra", "我是在拦截器中附加的参数");
                    callback.onContinue(postcard);
                }
            });

            MainLooper.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ab.create().show();
                }
            });
        } else {
            callback.onContinue(postcard);
        }

    }

    @Override
    public void init(Context context) {
        //拦截器的初始化，会在sdk初始化的时候调用。只会执行一次
    }
}
