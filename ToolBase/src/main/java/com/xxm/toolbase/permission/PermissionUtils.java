package com.xxm.toolbase.permission;

import android.app.Activity;
import android.content.DialogInterface;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xxm.toolbase.R;
import com.xxm.toolbase.view.dlg.DlgSys;

import io.reactivex.functions.Consumer;

/**
 * Created by shuaqq on 2017/4/7.
 */

public class PermissionUtils {

    public static void dealPermission(final Activity activity, final PermissionListener listener, final String... strings) {
        new RxPermissions(activity)
                .request(strings)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
//                    UIHelper.ToastMessage(activity, aBoolean + "");
                        if (aBoolean) {
                            listener.permissionGranted(strings);
                        } else {
                            DlgSys.show(activity, R.string.permission_denied, R.string.permission_denied_content, R.string.cancel
                                    , new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            listener.permissionDenied(strings);
                                        }
                                    }, R.string.settings
                                    , new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            com.xxm.toolbase.utils.PermissionUtils.startPermissionManager(activity);
                                        }
                                    });
                        }
                    }
                });
    }

}
