package com.xxm.toolbase.base;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.jaeger.library.StatusBarUtil;
import com.xxm.toolbase.utils.AppManager;
import com.xxm.toolbase.utils.UIHelper;
import com.xxm.toolbase.view.dlg.DlgProcessBar;
import com.xxm.toolhttp.retrofit.bus.LiveDataBusBean;
import com.xxm.toolhttp.retrofit.bus.LiveDataBusUtil;

/**
 * Created by xxm on 2018/11/29 0029
 */
public abstract class BaseActivity extends AppCompatActivity implements Observer<LiveDataBusBean> {

    public static String TAG = "BaseActivity";
    protected BaseActivity mContext;
    public boolean isActivity;//标识页面是否在活动
    public boolean isDestoryed = false;//页面是否被销毁
    protected int flags;
    protected Object[] objects;// 传递的数据
    protected String intentMsg;// 传递数据的TAG

    private String[] observerKeys;//liveDataBus订阅的key数组

    protected DlgProcessBar mDlgProcessBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(this);
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        AppManager.addActivity(this);
        if (UIHelper.scrW == 0) {
            UIHelper.getScreenInfo(this);
        }
        if (savedInstanceState != null) {
            objects = (Object[]) savedInstanceState.getSerializable("value");
        }
        Intent intent = getIntent();
        intentMsg = intent.getStringExtra("key");
        if (intent.getSerializableExtra("values") != null) {
            objects = (Object[]) intent.getSerializableExtra("values");
        }
        if (objects == null)
            objects = new Object[]{};

        setContentView(initContentView(savedInstanceState));

        mContext = this;

        initView();
        handleObject(intentMsg, objects);// 处理传递过来的数据
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        intentMsg = intent.getStringExtra("key");
        if (intent.getSerializableExtra("values") != null) {
            objects = (Object[]) intent.getSerializableExtra("values");
        }
    }

    public abstract int initContentView(Bundle savedInstanceState);

    public void initView() {
        mDlgProcessBar = new DlgProcessBar(mContext);
    }

    public void initData() {

    }


    public void showProcessBar() {
        if (isActivity) {
            if (mDlgProcessBar == null) {
                mDlgProcessBar = new DlgProcessBar(mContext);
            }
            mDlgProcessBar.doShow();
        }
    }

    public void dismissProcessBar() {
        if (isActivity) {
            if (mDlgProcessBar == null) {
                mDlgProcessBar = new DlgProcessBar(mContext);
            }
            mDlgProcessBar.doDismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivity = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivity = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        isDestoryed = true;
        super.onDestroy();
    }

    public void handleObject(String tag, Object... objects) {
        if (tag == null) {
            tag = BaseConstants.INTENT_TAG;
        }
    }

    /**
     * 订阅liveDataBus
     *
     * @param observerKeys 订阅的key数组
     */
    public void observerLiveDataBus(String[] observerKeys) {
        this.observerKeys = observerKeys;
        for (String tarKey : observerKeys) {
            LiveDataBusUtil.getInstance().observerBus(tarKey, LiveDataBusBean.class,this, this);
        }
    }

    /**
     * LiveDataBus接收的数据
     *
     * @param liveDataBusBean
     */
    public void handLiveDataBusBean(LiveDataBusBean liveDataBusBean) {
    }

    public void goToActivity(Class<?> cls) {
        goToActivity(cls, BaseConstants.INTENT_TAG);
    }

    public void goToActivity(Class<?> cls, Object... object) {
        goToActivity(cls, BaseConstants.INTENT_TAG, object);
    }

    public void goToActivity(Class<?> cls, String tag, Object... object) {
        goToActivity(cls, tag, object, 0);
    }

    public void goToActivity(Class<?> cls, String tag, Object[] objects, int request) {
        goToActivity(cls, tag, flags, request, objects);
        flags = 0;
    }

    public void goToActivity(Class<?> cls, int flag, Object... object) {
        goToActivity(cls, BaseConstants.INTENT_TAG, flag, 0, object);
    }

    public void goToActivity(Class<?> cls, int flag, int requestCode, Object... object) {
        goToActivity(cls, BaseConstants.INTENT_TAG, flag, requestCode, object);
    }

    public void goToActivity(Class<?> cls, String tag, int flag, int requestCode, Object... object) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.putExtra("key", tag);
        if (flag != 0)
            intent.setFlags(flag);
        if (object != null) {
            intent.putExtra("values", object);
        }
        if (requestCode > 0) {
            startActivityForResult(intent, requestCode);
        } else {
            startActivity(intent);
        }
    }

    /**
     * 跳转外部浏览器
     *
     * @param url
     */
    public void startBrowserWebUrl(String url) {
        if (TextUtils.isEmpty(url)) return;
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void onChanged(@Nullable LiveDataBusBean liveDataBusBean) {
        if (liveDataBusBean != null) {
            handLiveDataBusBean(liveDataBusBean);
        }

    }
}
