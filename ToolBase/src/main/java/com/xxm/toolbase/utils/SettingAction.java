package com.xxm.toolbase.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 设置action
 *
 * @author zhangbp
 */
public class SettingAction {

    private static String lionMarketSetting = "lionMarketSetting";
    public static int installPosition_default = 0;
    public static int installPosition_phone = 1;
    public static int installPosition_sd = 2;

    /**
     * 设置是否允许
     *
     * @param isAllow
     */

    public static void setWifiDownload(Context mContext, boolean isAllow) {
        Editor medit = getSetting(mContext).edit();
        medit.putBoolean("isWifiDownload", isAllow);
        medit.apply();
    }

    /**
     * 仅在wifi网络下载
     *
     * @param mContext
     * @return
     */
    public static boolean isWifiDownload(Context mContext) {
        return getSetting(mContext).getBoolean("isWifiDownload", false);
    }

    /**
     * 设置应用安装位置
     *
     * @param isAllow
     */

    public static void setInstallPosition(Context mContext, int type) {
        Editor medit = getSetting(mContext).edit();
        medit.putInt("installPosition", type);
        medit.apply();
    }

    /**
     * 读取安装的位置
     *
     * @param mContext
     * @return
     */

    public static int getInstallPosition(Context mContext) {
        return getSetting(mContext).getInt("setInstallPosition", installPosition_default);
    }


    /**
     * 设置root安装
     *
     * @param isAllow
     */

    public static void setRootInstall(Context mContext, boolean isAllow) {
        Editor medit = getSetting(mContext).edit();
        medit.putBoolean("isRootInstall", isAllow);
        medit.apply();
    }

    /**
     * root安装
     *
     * @param mContext
     * @return
     */
    public static boolean isRootInstall(Context mContext) {
        return getSetting(mContext).getBoolean("isRootInstall", true);
    }

    /**
     * 设置安装后删除安装包
     *
     * @param isAllow
     */

    public static void setInstalledDelPackage(Context mContext, boolean isAllow) {
        Editor medit = getSetting(mContext).edit();
        medit.putBoolean("isInstalledDelPackage", isAllow);
        medit.apply();
    }

    /**
     * 安装后删除安装包
     *
     * @param mContext
     * @return
     */
    public static boolean isInstalledDelPackage(Context mContext) {
        return getSetting(mContext).getBoolean("isInstalledDelPackage", true);
    }

    /**
     * 设置提醒更新
     *
     * @param isAllow
     */

    public static void setAlertUpdate(Context mContext, boolean isAllow) {
        Editor medit = getSetting(mContext).edit();
        medit.putBoolean("isAlertUpdate", isAllow);
        medit.apply();
    }

    /**
     * 是否提示更新
     *
     * @param mContext
     * @return
     */
    public static boolean isAlertUpdate(Context mContext) {
        return getSetting(mContext).getBoolean("isAlertUpdate", true);
    }

    /**
     * 创建快捷方式
     *
     * @param mContext
     */
    public static void setCreateShortcut(Context mContext) {
        Editor medit = getSetting(mContext).edit();
        medit.putBoolean("isCreateShortcut", true);
        medit.apply();
    }

    /**
     * 是否创建快捷方式
     *
     * @param mContext
     * @return
     */
    public static boolean isCreateShortcut(Context mContext) {
        return getSetting(mContext).getBoolean("isCreateShortcut", false);
    }

    /**
     * 是否显示新版本引导
     *
     * @param mContext
     * @return
     */
    public static boolean isReadNewVersionGuide(Context mContext) {
        return getSetting(mContext).getBoolean("isShowNewVersionGuide", false);
    }

    /**
     * 新版本引导
     *
     * @param mContext
     */
    public static void setReadNewVersionGuide(Context mContext) {
        Editor medit = getSetting(mContext).edit();
        medit.putBoolean("isShowNewVersionGuide", true);
        medit.apply();
    }


    private static SharedPreferences getSetting(Context mContext) {
        return mContext.getSharedPreferences(lionMarketSetting, 0);
    }


}
