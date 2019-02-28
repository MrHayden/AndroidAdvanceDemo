package com.xxm.toolbase.base;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.xxm.toolbase.R;
import com.xxm.toolbase.bus.LiveDataBusUtil;
import com.xxm.toolbase.entity.LiveDataBusBean;
import com.xxm.toolbase.utils.AppManager;
import com.xxm.toolbase.utils.UIHelper;
import com.xxm.toolbase.view.dlg.DlgProcessBar;

import butterknife.ButterKnife;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * Created by Administrator on 2018/11/29 0029
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, BGASwipeBackHelper.Delegate, Observer<LiveDataBusBean> {

    public static String TAG = "BaseActivity";
    protected BaseActivity mContext;
    public boolean isActivity;//标识页面是否在活动
    public boolean isDestoryed = false;//页面是否被销毁
    protected int flags;
    protected Object[] objects;// 传递的数据
    protected String intentMsg;// 传递数据的TAG
    protected BGASwipeBackHelper mSwipeBackHelper;

    private String[] observerKeys;//liveDataBus订阅的key数组

    protected DlgProcessBar mDlgProcessBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSwipeBackFinish();
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
        ButterKnife.bind(this);

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
    public void onClick(View v) {
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }


    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }

    @Override
    public void onChanged(@Nullable LiveDataBusBean liveDataBusBean) {
        if (liveDataBusBean != null) {
            handLiveDataBusBean(liveDataBusBean);
        }

    }
}
