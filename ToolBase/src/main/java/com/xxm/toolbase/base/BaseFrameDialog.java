package com.xxm.toolbase.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.xxm.toolbase.R;
import com.xxm.toolbase.utils.StringUtils;
import com.xxm.toolbase.utils.UIHelper;

import butterknife.ButterKnife;

/**
 * Created by shuaq on 2016/8/25.
 */

public class BaseFrameDialog extends Dialog implements View.OnClickListener {
    protected String TAG = getClass().getSimpleName();

    protected DlgCallback mCallback;

    protected Context mContext;

    protected View rootView;


    protected int flags;

    protected int width = WindowManager.LayoutParams.WRAP_CONTENT;
    protected int height = WindowManager.LayoutParams.WRAP_CONTENT;

    public BaseFrameDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }

    public BaseFrameDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        init();
    }

    protected BaseFrameDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        init();
    }

    private void init() {
        initBase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Window window = getWindow();
//        if (window != null) {
//            window.setBackgroundDrawableResource(R.color.transparent);
//            if (isFull) {
//                WindowManager.LayoutParams p = getWindow().getAttributes(); //获取对话框当前的参数值
//                p.height = UIHelper.scrH; //高度设置为屏幕的0.3
//                p.width = UIHelper.scrW; //宽度设置为屏幕的0.5
//                window.setAttributes(p); //设置生效
//            }
//        }
        rootView = getLayoutView();
        setContentView(rootView);
        ButterKnife.bind(this, rootView);
        setClick(initClickView(), initClickViewId());
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.color.transparent);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = width;
            lp.height = height;
            window.setAttributes(lp);
        }
        initThis();
        initListener();
        loadData();

    }

    protected void initBase() {

    }

    protected void initThis() {

    }

    protected void initListener() {

    }

    protected void loadData() {

    }

    protected int getLayout() {
        return 0;
    }

    protected View getLayoutView() {
        int layoutRes = getLayout();
        if (layoutRes != 0) {
            rootView = LayoutInflater.from(mContext).inflate(layoutRes, null, false);
        }
        return rootView;
    }


    @Override
    public void onClick(View v) {

    }

    protected void DisplayToast(String str) {
        UIHelper.ToastMessage(mContext, str);
    }

    protected void DisplayToast(int strRes) {
        UIHelper.ToastMessage(mContext, mContext.getString(strRes));
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
    public void goToWebUrl(String url) {
        if (StringUtils.isEmpty(url)) return;
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        mContext.startActivity(i);
    }

    protected <V extends View> V $(@IdRes int id) {
        return rootView.findViewById(id);
    }

    protected View[] initClickView() {
        return null;
    }

    protected int[] initClickViewId() {
        return null;
    }

    private void setClick(View[] views, int[] ids) {
        if (views != null)
            for (View view : views) if (view != null) view.setOnClickListener(this);
        if (ids != null)
            for (int id : ids) {
                View view = $(id);
                if (view != null)
                    view.setOnClickListener(this);
            }
    }

    public BaseFrameDialog setCallback(DlgCallback mCallback) {
        this.mCallback = mCallback;
        return this;
    }

    public void setDimAmount(float dimAmount) {
        if (getWindow() != null)
            getWindow().setDimAmount(dimAmount);
    }

    public void setAnimation(@StyleRes int res) {
        if (getWindow() != null)
            getWindow().setWindowAnimations(res);
    }

    public void HideKeyboard() {
        HideKeyboard(getCurrentFocus());
    }

    public void HideKeyboard(View v) {
        InputMethodManager imm;
        if (v == null) {
            return;
        }
        imm = (InputMethodManager) v.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        v.clearFocus();
    }

    // 显示虚拟键盘
    public void ShowKeyboard(View v) {
        InputMethodManager imm;
        if (v == null) {
            return;
        }
        imm = (InputMethodManager) v.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
        v.requestFocus();
    }

    public interface DlgCallback {
        void callback(int id, Object... objects);
    }

    @Override
    public void show() {
        if (!((BaseActivity) mContext).isFinishing())
            super.show();
    }
}
