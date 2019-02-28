package com.xxm.toolbase.view.dlg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xxm.toolbase.R;


public class DlgSys {

    public static MaterialDialog show(Context context, String title, CharSequence msg, View view, String leftStr, final OnClickListener listenerLeft, String rightStr,
                                      final OnClickListener listenerRight) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
        if (!TextUtils.isEmpty(title))
            builder.title(title);
        if (!TextUtils.isEmpty(msg))
            builder.content(msg);
        if (view != null)
            builder.customView(view, false);
//        builder.input("sdfsdf", "sdfsdfsd", new MaterialDialog.InputCallback() {
//            @Override
//            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
//
//            }
//        });
        if (!TextUtils.isEmpty(leftStr)) {
            builder.negativeText(leftStr);
            builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                    if (listenerLeft != null) {
                        listenerLeft.onClick(materialDialog, DialogInterface.BUTTON_NEGATIVE);
                    }
                }
            });
        }
        if (!TextUtils.isEmpty(rightStr)) {
            builder.positiveText(rightStr);
            builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction) {
                    if (listenerRight != null) {
                        listenerRight.onClick(materialDialog, DialogInterface.BUTTON_POSITIVE);
                    }
                }
            });
        }
        MaterialDialog dlg = new DlgDefault(builder);
        dlg.show();
        return dlg;
    }

    public static MaterialDialog show(Context context, int restitle, int resmsg,
                                      int resleftStr, OnClickListener listenerLeft, int resrightStr,
                                      OnClickListener listenerRight) {
        return show(context, restitle != 0 ? context.getString(restitle) : ""
                , resmsg != 0 ? context.getString(resmsg) : "", resleftStr != 0 ? context.getString(resleftStr) : "", listenerLeft
                , resrightStr != 0 ? context.getString(resrightStr) : "", listenerRight);
    }

    public static MaterialDialog show(Context context, String title, CharSequence msg,
                                      String leftStr, OnClickListener listenerLeft, String rightStr,
                                      OnClickListener listenerRight) {
        return show(context, title, msg, null, leftStr, listenerLeft, rightStr, listenerRight);

    }

    public static MaterialDialog show(Context context, int restitle, int resmsg,
                                      int resleftStr, OnClickListener listenerLeft) {
        return show(context, restitle != 0 ? context.getString(restitle) : ""
                , resmsg != 0 ? context.getString(resmsg) : "", resleftStr != 0 ? context.getString(resleftStr) : "", listenerLeft);
    }

    public static MaterialDialog show(Context context, String title, CharSequence msg,
                                      String leftStr, OnClickListener listenerLeft) {
        return show(context, title, msg, leftStr, listenerLeft, null, null);
    }

    public static MaterialDialog show(Context context, int restitle, int resmsg) {
        return show(context, context.getString(restitle), context.getString(resmsg));
    }

    public static MaterialDialog show(Context context, String title, CharSequence msg) {
        return show(context, title, msg, null, null, null, null);
    }

//    public static ProgressBar showDownloadDlg(Context context) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("正在下载新版本");
//
//        final LayoutInflater inflater = LayoutInflater.from(context);
//        View v = inflater.inflate(R.layout.update_progress, null);
//        ProgressBar mProgress = (ProgressBar) v
//                .findViewById(R.id.update_progress);
//        TextView mProgressText = (TextView) v
//                .findViewById(R.id.update_progress_text);
//
//        builder.setView(v);
//        builder.setNegativeButton("取消", new OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        builder.setOnCancelListener(new OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                dialog.dismiss();
//            }
//        });
//        builder.create();
//        builder.show();
//        return mProgress;
//    }

    public static MaterialDialog showDlg(Context mContext, int layoutId) {
        MaterialDialog materialDialog = null;
        materialDialog = new MaterialDialog.Builder(mContext).customView(layoutId, false).build();
        if (materialDialog != null) {
            materialDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
            materialDialog.show();
        }
        return materialDialog;
    }
}
