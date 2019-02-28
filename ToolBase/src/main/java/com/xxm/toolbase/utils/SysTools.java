package com.xxm.toolbase.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangbp
 */

public class SysTools {
    /**
     * 生成MD5
     *
     * @author zhangbp
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();

    }

    /**
     * 读取manifest里的meta-data数据
     *
     * @param mContext
     * @param name
     * @return
     * @author zhangbp
     */
    public static String getMetaData(Context mContext, String name) {
        ApplicationInfo appInfo;
        try {
            appInfo = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData == null ? null : appInfo.metaData.getString(name);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断sdCard的状态
     *
     * @author zhangbp
     */
    public static boolean sdCardIsExsit() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 读取sdCard的路径
     *
     * @author zhangbp
     */
    public static String getSdCardPath() {
        if (sdCardIsExsit()) {
            return Environment.getExternalStorageDirectory().getPath();
        }
        return null;
    }

    /**
     * dip转px
     *
     * @author zhangbp
     */

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转dip
     *
     * @author zhangbp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 读取系统给应用分配的内存(MB)
     *
     * @return
     * @author zhangbp
     */
    public static int getRunMemory(Context mcontxt) {
        return ((ActivityManager) mcontxt.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
    }

    /**
     * 获取链接的后缀名
     *
     * @param url
     * @return
     */
    public static String getSuffix(String url) {
        Pattern pattern = Pattern.compile("\\S*[?]\\S*");
        if (TextUtils.isEmpty(url))
            return "";
        Matcher matcher = pattern.matcher(url);

        String[] spUrl = url.split("/");
        int len = spUrl.length;
        String endUrl = spUrl[len - 1];

        if (matcher.find()) {
            String[] spEndUrl = endUrl.split("\\?");
            return spEndUrl[0].split("\\.")[1];
        }
        String[] suffix = endUrl.split("\\.");
        if (suffix.length > 1)
            return endUrl.split("\\.")[1];
        else
            return "";
    }

    /**
     * checkPermissions
     *
     * @param context
     * @param permission
     * @return true or false
     */
    public static boolean checkPermissions(Context context, String permission) {
        PackageManager localPackageManager = context.getPackageManager();
        return localPackageManager.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Determine the current networking is WIFI
     *
     * @param context
     * @return
     */
    public static boolean currentNoteworkTypeIsWIFI(Context context) {
        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectionManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * Judge wifi is available
     *
     * @param inContext
     * @return
     */
    public static boolean isWiFiActive(Context inContext) {
        if (checkPermissions(inContext, "android.permission.ACCESS_WIFI_STATE")) {
            Context context = inContext.getApplicationContext();
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Testing equipment networking and networking WIFI
     *
     * @param context
     * @return true or false
     */
    public static boolean isNetworkAvailable(Context context) {
        if (checkPermissions(context, "android.permission.INTERNET")) {
            ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cManager == null)
                return false;
            NetworkInfo info = cManager.getActiveNetworkInfo();
            return info != null && info.isAvailable();

        } else {
            return false;
        }
    }

    /**
     * Get the current time format yyyy-MM-dd HH:mm:ss
     *
     * @return
     */

    @SuppressLint("SimpleDateFormat")
    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return localSimpleDateFormat.format(date);
    }

    /**
     * get APPKEY
     *
     * @return appkey
     */
    public static String getAppKey(Context paramContext) {
        if (paramContext == null) {
            return "";
        }
        String umsAppkey;
        try {
            PackageManager localPackageManager = paramContext.getPackageManager();
            ApplicationInfo localApplicationInfo = localPackageManager.getApplicationInfo(paramContext.getPackageName(), 128);
            if (localApplicationInfo != null) {
                String str = localApplicationInfo.metaData.getString("UMS_APPKEY");
                if (str != null) {
                    umsAppkey = str;
                    return umsAppkey;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * get currnet activity's name
     *
     * @param context
     * @return
     */
    public static String getActivityName(Context context) {
        if (context == null) {
            return "";
        }
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (checkPermissions(context, "android.permission.GET_TASKS")) {
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            return cn.getShortClassName();
        } else {
            return "";
        }

    }

    /**
     * get PackageName
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            applicationInfo = null;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }


    /**
     * get OS number
     *
     * @return
     */
    public static String getOsVersion() {
        String osVersion = "";
        osVersion = Build.VERSION.RELEASE;
        return osVersion;
    }

    /**
     * get deviceid
     *
     * @param context add <uses-permission android:name="READ_PHONE_STATE" />
     * @return
     */
    public static String getDeviceID(Context context) {
        if (context == null) {
            return "";
        }
        if (checkPermissions(context, "android.permission.READ_PHONE_STATE")) {
            String deviceId = "";
            if (checkPhoneState(context)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                deviceId = tm.getDeviceId();
            }
            if (deviceId != null) {
                return deviceId;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String getSubscriberId(Context context) {
        if (context == null) {
            return "";
        }
        if (checkPermissions(context, "android.permission.READ_PHONE_STATE")) {
            String deviceId = "";
            if (checkPhoneState(context)) {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                deviceId = tm.getSubscriberId();
            }
            if (deviceId != null) {
                return deviceId;
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String getAndroidId(Context context) {
        if (context == null) {
            return "";
        }
        return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * check phone _state is readied ;
     *
     * @param context
     * @return
     */
    public static boolean checkPhoneState(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.checkPermission("android.permission.READ_PHONE_STATE", context.getPackageName()) == 0;
    }

    /**
     * get sdk number
     *
     * @param paramContext
     * @return
     */
    public static String getSdkVersion(Context paramContext) {
        String osVersion = "";
        if (!checkPhoneState(paramContext)) {
            osVersion = Build.VERSION.RELEASE;
            return osVersion;
        } else {
            return null;
        }
    }

    /**
     * Get the version number of the current program
     *
     * @param context
     * @return
     */

    public static String getCurVersion(Context context) {
        String curversion = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            curversion = pi.versionName;
            if (curversion == null || curversion.length() <= 0) {
                return "";
            }
        } catch (Exception ignored) {

        }
        return curversion;
    }

    /**
     * Get the current send model
     *
     * @param context
     * @return
     */
    public static int getReportPolicyMode(Context context) {
        String str = context.getPackageName();
        SharedPreferences localSharedPreferences = context.getSharedPreferences("ums_agent_online_setting_" + str, 0);
        int type = localSharedPreferences.getInt("ums_local_report_policy", 0);
        return type;
    }

    /**
     * Get the base station information
     *
     * @throws Exception
     */
    public static JSONObject getCellInfo(Context context) throws Exception {
        JSONObject cell = new JSONObject();
        TelephonyManager mTelNet = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        CellLocation mCellLocation = mTelNet.getCellLocation();
        GsmCellLocation location = null;
        if (mCellLocation instanceof GsmCellLocation) {
            location = (GsmCellLocation) mTelNet.getCellLocation();
        }
        if (location == null) {
            return null;
        }

        int mcc = 0, mnc = 0, cid = 0, lac = 0;

        String operator = mTelNet.getNetworkOperator();
        if (operator != null && !operator.equals("")) {
            mcc = Integer.parseInt(operator.substring(0, 3));
            mnc = Integer.parseInt(operator.substring(3));
            cid = location.getCid();
            lac = location.getLac();
        }

        cell.put("mcc", mcc);
        cell.put("mnc", mnc);
        cell.put("lac", lac);
        cell.put("cid", cid);

        return cell;
    }

    /**
     * To determine whether it contains a gyroscope
     *
     * @return
     */
    public static boolean isHaveGravity(Context context) {
        SensorManager manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (manager == null) {
            return false;
        }
        return true;
    }

    /**
     * Get the current networking
     *
     * @param context
     * @return WIFI or MOBILE
     */
    public static String getNetworkType(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int type = manager.getNetworkType();
        String typeString = "UNKNOWN";
        if (type == TelephonyManager.NETWORK_TYPE_CDMA) {
            typeString = "CDMA";
        }
        if (type == TelephonyManager.NETWORK_TYPE_EDGE) {
            typeString = "EDGE";
        }
        if (type == TelephonyManager.NETWORK_TYPE_EVDO_0) {
            typeString = "EVDO_0";
        }
        if (type == TelephonyManager.NETWORK_TYPE_EVDO_A) {
            typeString = "EVDO_A";
        }
        if (type == TelephonyManager.NETWORK_TYPE_GPRS) {
            typeString = "GPRS";
        }
        if (type == TelephonyManager.NETWORK_TYPE_HSDPA) {
            typeString = "HSDPA";
        }
        if (type == TelephonyManager.NETWORK_TYPE_HSPA) {
            typeString = "HSPA";
        }
        if (type == TelephonyManager.NETWORK_TYPE_HSUPA) {
            typeString = "HSUPA";
        }
        if (type == TelephonyManager.NETWORK_TYPE_UMTS) {
            typeString = "UMTS";
        }
        if (type == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
            typeString = "UNKNOWN";
        }
        if (type == TelephonyManager.NETWORK_TYPE_1xRTT) {
            typeString = "1xRTT";
        }
        if (type == 11) {
            typeString = "iDen";
        }
        if (type == 12) {
            typeString = "EVDO_B";
        }
        if (type == 13) {
            typeString = "LTE";
        }
        if (type == 14) {
            typeString = "eHRPD";
        }
        if (type == 15) {
            typeString = "HSPA+";
        }

        return typeString;
    }

    /**
     * Determine the current network type
     *
     * @param context
     * @return
     */
    public static boolean isNetworkTypeWifi(Context context) {
        // TODO Auto-generated method stub

        if (checkPermissions(context, "android.permission.INTERNET")) {
            ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cManager == null)
                return false;
            NetworkInfo info = cManager.getActiveNetworkInfo();
            return info != null && info.isAvailable() && info.getTypeName().equals("WIFI");
        } else {
            return false;
        }

    }

    /**
     * Get the current application version number
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        String versionName = "";
        try {
            if (context == null) {
                return "";
            }
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * Set the output log
     *
     * @param tag
     * @param log
     */

    public static void printLog(String tag, String log) {

    }

    @SuppressLint("DefaultLocale")
    public static String getNetworkTypeWIFI2G3G(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = cm.getActiveNetworkInfo();
        String type = info.getTypeName().toLowerCase();
        if (!type.equals("wifi")) {
            type = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getExtraInfo();
        }
        return type;

    }

    /**
     * Get device name, manufacturer + model
     *
     * @return device name
     */
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    /**
     * Capitalize the first letter
     *
     * @param s model,manufacturer
     * @return Capitalize the first letter
     */
    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    /**
     * 把中文转成Unicode码
     *
     * @param str
     * @return
     */
    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }

    /**
     * 返回合适的次数
     *
     * @param count
     * @return
     * @author zhangbp
     */
    public static String getSuitableCount(long count) {
        String fileSizeString = count + "";
        if (count > 10000) {
            float c = count / 10000f;
            fileSizeString = new DecimalFormat("#.0").format(c) + "万";
        }
        return fileSizeString;
    }

    /**
     * 返回合适的单位
     *
     * @param bytes
     * @return
     * @author zhangbp
     */
    public static String getSuitableSize(long bytes) {
        NumberFormat f = NumberFormat.getInstance();
        f.setMaximumFractionDigits(1);
        String fileSizeString = "";
        Double a = (double) (bytes / 1024);
        if (bytes < 1024) {
            fileSizeString = bytes + "B";
        } else if (bytes < 1024 * 1024) { // KB
            fileSizeString = f.format(a) + "KB";
        } else if (bytes < 1024 * 1024 * 1024) { // MB
            a = a / 1024;
            fileSizeString = f.format(a) + "M";
        } else if (bytes / 1024 < 1024 * 1024 * 1024) { // GB
            a = a / (1024 * 1024);
            fileSizeString = f.format(a) + "G";
        }
        return fileSizeString;
    }

    /**
     * 格式化时间
     *
     * @param time
     * @param exactly
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatTime(long time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(new Date(time));
    }

    /**
     * 返回app的版本名称
     *
     * @param mContext
     * @return
     */
    public static String getVersionName(Context mContext) {
        PackageInfo info;
        try {
            info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取versionCode
     *
     * @return
     * @author zhangbp
     */

    public static int getVersionCode(Context mContext) {
        try {
            PackageInfo pi = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return pi.versionCode;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 根据url生成key
     *
     * @param url
     * @return
     * @author zhangbp
     */
    public static String generateKey(String url) {
        return String.format("lion_%1$s", md5(url));
    }

    /**
     * ConnectivityManager.TYPE_WIFI 网络的类型
     *
     * @param mContext
     * @return
     */
    public static int getNetWordType(Context mContext) {
        ConnectivityManager connectMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null) {
            return info.getType();
        }
        return -1;

    }

    public static boolean isOneActivity(Context mContext) {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(100);
        String packageName = mContext.getPackageName();
        boolean isOneActivity = false;
        for (RunningTaskInfo info : list) {
            if (info.topActivity.toString().contains(packageName + "/")) {
                isOneActivity = info.numActivities == 1;
            }
        }
        return isOneActivity;
    }

    /**
     * 是否安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    /**
     * 检查是否存在SDCard
     *
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^[1][358]\\d{9}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 是否安装google服务
     *
     * @param context
     * @return
     */
    public static boolean isGooglePlayServicesAvailable(Context context) {
        PackageManager localPackageManager = context.getPackageManager();
        try {
            localPackageManager.getPackageInfo("com.android.vending", 64);
        } catch (NameNotFoundException localNameNotFoundException) {
            Log.w("GooglePlayServicesUtil", "Google Play Store is missing.");
            return false;
        }
        try {
            localPackageManager.getPackageInfo("com.google.android.gms", 64);
        } catch (NameNotFoundException localNameNotFoundException1) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return false;
        }
        try {
            localPackageManager.getApplicationInfo("com.google.android.gms", 0);
        } catch (NameNotFoundException localNameNotFoundException2) {
            Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.");
            localNameNotFoundException2.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getMac() {
        String var1 = "";

        try {
            var1 = getMacShell();
            if (TextUtils.isEmpty(var1)) {
                Log.w("DeviceConfig", "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
            }

            if (TextUtils.isEmpty(var1)) {
                var1 = getMacByJava();
                if (TextUtils.isEmpty(var1)) {
                    Log.w("DeviceConfig", "Could not get mac address by java");
                }
            }
            return var1;
        } catch (Exception var3) {
            Log.w("DeviceConfig", "Could not get mac address." + var3.toString());
            return var1;
        }
    }

    private static String getMacByJava() {
        try {
            Enumeration var0 = NetworkInterface.getNetworkInterfaces();

            while (var0.hasMoreElements()) {
                NetworkInterface var1 = (NetworkInterface) var0.nextElement();
                if (var1.getName().equals("wlan0")) {
                    byte[] var2 = var1.getHardwareAddress();
                    if (var2 != null && var2.length != 0) {
                        StringBuilder var3 = new StringBuilder();
                        for (byte var7 : var2) {
                            var3.append(String.format("%02X:", var7));
                        }
                        if (var3.length() > 0) {
                            var3.deleteCharAt(var3.length() - 1);
                        }
                        return var3.toString();
                    }
                    return null;
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }
        return null;
    }

    private static String getMacShell() {
        String[] var0 = new String[]{"/sys/class/net/wlan0/address", "/sys/class/net/eth0/address", "/sys/devices/virtual/net/wlan0/address"};

        for (String aVar0 : var0) {
            try {
                String var1 = reaMac(aVar0);
                if (var1 != null) {
                    return var1;
                }
            } catch (Exception var4) {
//                Log.e("DeviceConfig", "open file  Failed", var4);
            }
        }
        return null;
    }

    private static String reaMac(String var0) throws FileNotFoundException {
        String var1 = null;
        FileReader var2 = new FileReader(var0);
        BufferedReader var3 = null;
        try {
            var3 = new BufferedReader(var2, 1024);
            var1 = var3.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                var2.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
            if (var3 != null) {
                try {
                    var3.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return var1;
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return "";
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /**
     * 卸载app
     *
     * @param mContext
     * @param packageName
     */
    public static void unInstall(Context mContext, String packageName) {
        Uri uri = Uri.fromParts("package", packageName, null);
        Intent it = new Intent(Intent.ACTION_DELETE, uri);
        mContext.startActivity(it);
    }

    /**
     * 打开app
     *
     * @param mContext
     * @param packageName
     */
    public static void openApp(Context mContext, String packageName) {
        if (packageName != null && !packageName.equals("")) {
            Intent mIntent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
            if (mIntent != null) {
                mContext.startActivity(mIntent);
            }
        }

    }

    /**
     * 安装app
     *
     * @param mContext
     * @param packageName
     */

    public static void installApp(Context mContext, String appPath) {
        InstallHelper.getInstance().addInstallTask(appPath, mContext.getApplicationContext());
    }

    /**
     * 删除文件
     *
     * @param filePaths
     */
    @SuppressLint("NewApi")
    public static void delApkFile(final String... filePaths) {
        AsyncTask<Object, Object, Object> mAsyncTask = new AsyncTask<Object, Object, Object>() {

            @Override
            protected Object doInBackground(Object... params) {
                // TODO Auto-generated method stub
                if (filePaths != null) {
                    for (String filepath : filePaths) {
                        new File(filepath).delete();
                    }
                }
                return null;
            }

        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            mAsyncTask.execute();
        }
    }


    @SuppressLint("NewApi")
    public static boolean isNotificationEnabled(Context context) {
        final String CHECK_OP_NO_THROW = "checkOpNoThrow";
        final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
      /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void checkIsNotificationEnable(Context context) {
        if (!isNotificationEnabled(context)) {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
        }
    }

    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    public static String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null && !StringUtils.isEmptyList(manager.getRunningAppProcesses())){
            for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
                if (process.pid == pid) {
                    processName = process.processName;
                }
            }
        }
        return processName;
    }

    public static boolean isMainProcess(Context context) {
        return context.getPackageName().equals(getCurrentProcessName(context));
    }
}
