package com.xxm.toolbase.utils;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;

import com.xxm.toolbase.R;
import com.xxm.toolbase.view.dlg.DlgSys;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by chenyong on 16/2/29.
 */
public class PermissionUtils {

    private static final String TAG = PermissionUtils.class.getSimpleName();

    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    public final static int REQUEST_CAMERA = 0;
    public final static int REQUEST_RECORD_AUDIO = 1;
    public final static int SYSTEM_ALERT_WINDOW = 2;
    public final static int SYSTEM_WRITE_EXTERNAL_STORAGE = 3;
    public final static int SYSTEM_COARSE_LOCATION = 4;
    public final static int SYSTEM_FINE_LOCATION = 5;
    public final static int SYSTEM_READ_PHONE_STATE = 5;

    /**
     * 权限类型，枚举类型
     */
    public enum PermissionType {
        CAMERA,                 // 相机
        RECORD_AUDIO,           // 录音
        SYSTEM_ALERT_WINDOW,    // 悬浮窗
        WRITE_EXTERNAL_STORAGE, // sdcard
        COARSE_LOCATION,        // 粗略定位
        READ_PHONE_STATE,       // 电话状态
        FINE_LOCATION,          // 精细定位
    }

    /**
     * 检查相机权限
     */
    public static boolean checkCamera(Context context) {
        return checkPermissionWithType(context, PermissionType.CAMERA);
    }

    /**
     * 检查录音权限
     */
    public static boolean checkRecordAudio(Context context) {
        return checkPermissionWithType(context, PermissionType.RECORD_AUDIO);
    }

    /**
     * 检查悬浮窗权限
     */
    public static boolean checkSystemAlertWindow(Context context) {
        return checkPermissionWithType(context, PermissionType.SYSTEM_ALERT_WINDOW);
    }

    /**
     * 检查sdcard权限
     */
    public static boolean checkSdcardAlertWindow(Context context) {
        return checkPermissionWithType(context, PermissionType.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 检查定位权限
     */
    public static boolean checkAccessLocation(Context context) {
        return checkPermissionWithType(context, PermissionType.COARSE_LOCATION)
                || checkPermissionWithType(context, PermissionType.FINE_LOCATION);
    }

    /**
     * 检查sdcard权限
     */
    public static boolean checkPhoneState(Context context) {
        return checkPermissionWithType(context, PermissionType.READ_PHONE_STATE);
    }

    /**
     * 检查是否具有type指定的权限
     */
    private static boolean checkPermissionWithType(Context context, PermissionType type) {
        boolean result = true;
        String permission = "" + type;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                AppOpsManager mgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                PackageManager pm = context.getPackageManager();
                PackageInfo info;
                info = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
                Class<?> classType = AppOpsManager.class;
                Field f = classType.getField("OP_" + permission);
                Method m = classType.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                m.setAccessible(true);
                int status = (Integer) m.invoke(mgr, f.getInt(mgr), info.applicationInfo.uid, info.packageName);
                // chenyong1 MIUI8用4来做权限标示
                result = (status != AppOpsManager.MODE_ERRORED && status != AppOpsManager.MODE_IGNORED && status != 4);
            } catch (Exception e) {
                Log.e(TAG, "权限检查出错时默认返回有权限，异常代码：" + e);
            }
        } else {
            result = checkPermission(context, "android.permission." + permission);
        }
        Log.d(TAG, "call checkPermissionWithType: " + type + " = " + result);
        return result;
    }

    /**
     * 检查某个权限（属于静态检查，即该权限是否在manifest文件中声明）
     */
    private static boolean checkPermission(Context context, String permission) {
        PackageManager pm = context.getPackageManager();
        return (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission(permission, context.getPackageName()));
    }

    /**
     * 跳转到APP权限设置界面
     */
    public static void startPermissionManager(Activity activity) {
        Intent intent;
        if (isMIUI(activity) || isMIUIRom(activity)) {
            PackageManager pm = activity.getPackageManager();
            PackageInfo info;
            try {
                info = pm.getPackageInfo(activity.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, e.toString());
                return;
            }
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            // i.setClassName("com.android.settings", "com.miui.securitycenter.permission.AppPermissionsEditor");
            intent.putExtra("extra_pkgname", activity.getPackageName());      // for MIUI 6
            intent.putExtra("extra_package_uid", info.applicationInfo.uid);  // for MIUI 5
        } else {
            intent = new Intent();
            intent.setAction("android.intent.action.MAIN");
            intent.setClassName("com.android.settings", "com.android.settings.ManageApplications");
        }
        if (isIntentAvailable(activity, intent)) {
            activity.startActivity(intent);
        }
    }

    /**
     * 当在系统权限管理中没有相应权限时，显示开启权限提示弹窗
     */
    public static void showPermissionDialog(final Activity activity, final PermissionType type) {
        switch (type) {
            case CAMERA:
                DlgSys.show(activity,
                        R.string.title_camera_permission,
                        R.string.check_camera_video_message,
                        R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }, R.string.settings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startPermissionManager(activity);
                            }
                        });
                break;
            case RECORD_AUDIO:
                DlgSys.show(activity,
                        R.string.title_record_audio_permission,
                        R.string.message_record_audio_permission,
                        R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }, R.string.settings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startPermissionManager(activity);
                            }
                        });
                break;
            case SYSTEM_ALERT_WINDOW:
                DlgSys.show(activity,
                        R.string.title_floating_window_permission,
                        R.string.message_floating_window_permission,
                        R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }, R.string.settings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startPermissionManager(activity);
                            }
                        });
                break;
            case WRITE_EXTERNAL_STORAGE:
                DlgSys.show(activity,
                        R.string.title_sdcard_permission,
                        R.string.message_sdcard_permission,
                        R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }, R.string.settings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startPermissionManager(activity);
                            }
                        });
                break;
            case COARSE_LOCATION: // fall through
            case FINE_LOCATION:
                DlgSys.show(activity,
                        R.string.title_location_permission,
                        R.string.message_location_permission,
                        R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }, R.string.settings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startPermissionManager(activity);
                            }
                        });
                break;
            case READ_PHONE_STATE:
                DlgSys.show(activity,
                        R.string.title_phone_permission,
                        R.string.message_phone_permission,
                        R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }, R.string.settings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startPermissionManager(activity);
                            }
                        });
                break;
            default:
                break;
        }
    }

    public static boolean isMIUIRom(final Context context) {
        try {
            return context.getPackageManager().getPackageInfo(
                    "com.miui.cloudservice", PackageManager.GET_CONFIGURATIONS) != null;
        } catch (final PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isMIUI(Context context) {
        boolean result = false;
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        if (isIntentAvailable(context, i)) {
            result = true;
        }
        return result;
    }

    public static boolean isIntentAvailable(final Context context, final Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        final List<ResolveInfo> list = packageManager.queryIntentActivities(
                intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
