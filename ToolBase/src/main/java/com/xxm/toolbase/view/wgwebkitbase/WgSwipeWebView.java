package com.xxm.toolbase.view.wgwebkitbase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.xxm.toolbase.R;


/**
 * Created by shuai on 2015/08/11.
 */
public class WgSwipeWebView extends RelativeLayout {

    private final static String TAG = "WgSwipeWebView";

    private WgWebViewBase vWebView;
    private ProgressBar vProgress;

    private Context mContext;
    protected View rootView;

    private String mUrl;

    private int mProgress = 0;

    public WgSwipeWebView(Context context) {
        super(context);
        init(context);
    }

    public WgSwipeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WgSwipeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context mContext) {
        this.mContext = mContext;
        rootView = LayoutInflater.from(mContext).inflate(R.layout.wg_webview, this, false);
        addView(rootView);
        initThis();
    }

    public WgWebViewBase getvWebView() {
        return vWebView;
    }


    protected void initThis() {

        vWebView = (WgWebViewBase) rootView.findViewById(R.id.wg_webview);
        vProgress = (ProgressBar) rootView.findViewById(R.id.wg_webview_progress);

        vWebView.setHorizontalScrollBarEnabled(false);//水平不显示
        vWebView.setVerticalScrollBarEnabled(false); //垂直不显示
        addJavascriptInterface(new AndroidtoJs(), "vestBagSnatch");

    }

    @Override
    public void setOverScrollMode(int overScrollMode) {
        super.setOverScrollMode(overScrollMode);
        if (vWebView != null)
            vWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    public void setProgress(int progress) {
        mProgress = progress;
        vProgress.setProgress(progress);
    }

    public void loadUrl(String url) {
        mUrl = url;
        vWebView.loadUrl(url);
    }

    public String getTitle() {
        return vWebView.getTitle();
    }

    public void reLoad() {
        vWebView.reload();
    }

    public void setWgInterface(WgWebViewInterface wgWebViewInterface) {
        vWebView.setClientInterface(wgWebViewInterface);

    }

    public void loadJavascriptMethod(String str) {
        str = "javascript:" + str;
        vWebView.loadUrl(str);
    }

    @SuppressLint("JavascriptInterface")
    public void addJavascriptInterface(Object javascriptObj, String interfaceName) {
        vWebView.addJavascriptInterface(javascriptObj, interfaceName);
    }

    public void pause() {
        if (vWebView != null)
            vWebView.onPause();
    }

    public void resume() {
        if (vWebView != null)
            vWebView.onResume();
    }

    public boolean keyDown() {
        if (vWebView.canGoBack()) {
            vWebView.goBack();
            return false;
        }
        return true;
    }

    public void destory() {
        if (vWebView != null) {
            vWebView.destroy();
        }
    }

    public void clearHistory() {
        vWebView.clearHistory();
    }

    public void setProgressbarEnable(boolean enable) {
        vWebView.setProgressBar(enable ? vProgress : null);
    }

    public void showProgress() {
        if (vWebView != null && vProgress != null)
            vWebView.setProgressBar(vProgress);
    }

    // 继承自Object类
    public class AndroidtoJs extends Object {

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void finishWebView() {
            if (mContext instanceof Activity)
                ((Activity) mContext).finish();
        }
    }
}
