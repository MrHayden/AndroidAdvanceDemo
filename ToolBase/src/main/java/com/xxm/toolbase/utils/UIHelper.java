package com.xxm.toolbase.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * 应用程序UI工具包：封装UI相关的一些操作
 */
public class UIHelper {

    public static int scrW;
    public static int scrH;

    // 回去手机设备基本信息
    public static void getScreenInfo(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        scrW = metric.widthPixels; // 屏幕宽度（像素）
        scrH = metric.heightPixels; // 屏幕高度（像素）
    }

    public static void ToastMessage(final Context cont, final int tMsg) {
        new Handler(cont.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (cont instanceof Activity && !((Activity) cont).isFinishing())
                    Toast.makeText(cont, tMsg, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(cont.getApplicationContext(), tMsg, Toast.LENGTH_SHORT).show();
            }
        }.sendEmptyMessage(0);
    }

    public static void ToastMessage(final Context cont, final String tMsg) {
        new Handler(cont.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (cont instanceof Activity && !((Activity) cont).isFinishing())
                    Toast.makeText(cont, tMsg, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(cont.getApplicationContext(), tMsg, Toast.LENGTH_SHORT).show();

            }
        }.sendEmptyMessage(0);
    }

    public static void ToastMessage(final Context cont, final String tMsg, final int time) {
        new Handler(cont.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (cont instanceof Activity && !((Activity) cont).isFinishing())
                    Toast.makeText(cont, tMsg, time).show();
                else
                    Toast.makeText(cont.getApplicationContext(), tMsg, time).show();

            }
        }.sendEmptyMessage(0);
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static View setView(View view, int w, int h) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null)
            lp = new ViewGroup.LayoutParams(w, h);
        else {
            lp.width = w;
            lp.height = h;
        }
        view.setLayoutParams(lp);
        return view;
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    public static void hideBottomUIMenu(Activity activity) {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
