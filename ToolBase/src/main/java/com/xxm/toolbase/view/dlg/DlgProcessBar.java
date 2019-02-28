package com.xxm.toolbase.view.dlg;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;

import com.xxm.toolbase.R;


public class DlgProcessBar {

    private Context mContext;
    private AppCompatDialog dlg;

    public boolean isShowing() {
        return dlg != null && dlg.isShowing();
    }

    public void doShow() {
        if (mContext != null) {
            if (dlg == null) {
                if (mContext != null) {
                    dlg = new AppCompatDialog(mContext, R.style.dialog_pb);
                    dlg.setCanceledOnTouchOutside(true);
                    dlg.setContentView(R.layout.dlg_process_bar);
                }
            }
            if (dlg != null && !dlg.isShowing()) {
                dlg.show();
            }
        }
    }

    public DlgProcessBar(Context context) {
        mContext = context;
    }


    public void doDismiss() {
        if (mContext != null && dlg != null && dlg.isShowing()) {
            dlg.dismiss();
        }
    }
}
