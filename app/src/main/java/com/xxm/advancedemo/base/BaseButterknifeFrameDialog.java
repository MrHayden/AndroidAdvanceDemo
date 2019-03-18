package com.xxm.advancedemo.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xxm.toolbase.base.BaseFrameDialog;

import butterknife.ButterKnife;

/**
 * Created by xxm on 2019/3/18 0018
 */
public class BaseButterknifeFrameDialog extends BaseFrameDialog {

    public BaseButterknifeFrameDialog(@NonNull Context context) {
        super(context);
    }

    public BaseButterknifeFrameDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseButterknifeFrameDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void initThis() {
        if (rootView != null)
            ButterKnife.bind(this, rootView);
    }
}
