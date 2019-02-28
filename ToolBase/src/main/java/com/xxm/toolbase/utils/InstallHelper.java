package com.xxm.toolbase.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Queue;

/**
 * app安装
 *
 * @author zhangbp
 */

public class InstallHelper extends Observable {

    public static InstallHelper mInstallHelper;
    public Queue<String> queue = new LinkedList<>();
    public Map<String, InstallTaskState> installTask = Collections.synchronizedMap(new HashMap<String, InstallTaskState>());
    public Context appContext;

    public static synchronized InstallHelper getInstance() {
        if (mInstallHelper == null) {
            mInstallHelper = new InstallHelper();
        }
        return mInstallHelper;
    }

    /**
     * 添加安装任务
     *
     * @param appFilePath
     * @param mContext
     */

    public void addInstallTask(String appFilePath, Context mContext) {
        appContext = mContext.getApplicationContext();

        if (!queue.contains(appFilePath)) {
            queue.add(appFilePath);
            InstallTaskState mInstallTaskState = new InstallTaskState();
            mInstallTaskState.status = SysTools.getSuffix(appFilePath).equals("cpk") ? InstallTaskState.Install_state_unzip : InstallTaskState.Install_state_ing;
            installTask.put(appFilePath, mInstallTaskState);
        }

        if (queue.size() == 1) {
            startInstall();
        }
        dataChange();
    }

    /**
     * 数据发生产变化
     *
     * @author zhangbp
     */

    private void dataChange() {
        setChanged();
        notifyObservers();
    }


    public Map<String, InstallTaskState> getInstallTaskState() {
        return installTask;
    }

    /**
     * 开始安装
     *
     * @author zhangbp
     */

    private void startInstall() {

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                String originalFilePath = queue.peek();
                if (!TextUtils.isEmpty(originalFilePath)) {
                    if (SysTools.getSuffix(originalFilePath).equals("cpk")) {
//                        installCpk(originalFilePath);
                    } else {
                        installApk(originalFilePath, originalFilePath);
                    }
                }
            }
        }, 200);
    }

    /**
     * cpk安装
     *
     * @param mContext
     * @param cpkFilePath
     */

//    private void installCpk(final String cpkFilePath) {
//
//        new CpkCopyData(appContext).copyData(cpkFilePath, new CpkCopyData.UnZipListener() {
//            private InstallTaskState mInstallTaskState = null;
//            private long lastTime;
//
//            @Override
//            public void onPer() {
//                // TODO Auto-generated method stub
//                mInstallTaskState = installTask.get(cpkFilePath);
//                if (mInstallTaskState != null) {
//                    mInstallTaskState.status = InstallTaskState.Install_state_unzip;
//                    dataChange();
//                }
//            }
//
//            @Override
//            public void onProgress(int progress, String progressTips) {
//                // TODO Auto-generated method stub
//
//                mInstallTaskState = installTask.get(cpkFilePath);
//                if (mInstallTaskState != null) {
//                    mInstallTaskState.status = InstallTaskState.Install_state_unzip;
//                    mInstallTaskState.progress = progress;
//                }
//
//                long temTime = System.currentTimeMillis();
//                if (temTime - lastTime > 1000) {
//                    lastTime = temTime;
//                    dataChange();
//                }
//            }
//
//            @Override
//            public void onError(String msg) {
//                // TODO Auto-generated method stub
//                dataChange();
//                UIHelper.ToastMessage(appContext, msg);
//                installTask.remove(cpkFilePath);
//            }
//
//            @Override
//            public void onComplete(String apkPath, String dataPath) {
//                // TODO Auto-generated method stub
//                if (apkPath != null) {
//                    installApk(apkPath, cpkFilePath);
//                } else {
//                    dataChange();
//                }
//            }
//        });
//    }

    /**
     * 开始安装apk
     *
     * @param apkPath
     */

    @SuppressLint("NewApi")
    private void installApk(final String apkPath, final String originalFilePath) {

        AsyncTask<Object, Object, Integer> mAsyncTask = new AsyncTask<Object, Object, Integer>() {

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                installTask.get(originalFilePath).status = InstallTaskState.Install_state_ing;
                dataChange();
            }

            @Override
            protected Integer doInBackground(Object... params) {
                if (SettingAction.isRootInstall(appContext) && ShellUtils.checkRootPermission()) {
                    int status = PackageUtils.installSilent(appContext, apkPath);
                    if (status != PackageUtils.INSTALL_SUCCEEDED) {
                        PackageUtils.installNormal(appContext, apkPath);
                        return -9999999;
                    } else {
                        return status;
                    }

                } else {
                    PackageUtils.installNormal(appContext, apkPath);
                    return -9999999;
                }
            }

            @Override
            protected void onPostExecute(Integer result) {
                // TODO Auto-generated method stub
                queue.remove(originalFilePath);
                installTask.remove(originalFilePath);
                dataChange();
                startInstall();
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            mAsyncTask.execute();
        }
    }

    /**
     * 安装状态
     *
     * @author zhangbp
     */
    public static class InstallTaskState {
        public static final int Install_state_unzip = 1;
        public static final int Install_state_ing = 2;
        public int status;
        public int progress;
    }

}
