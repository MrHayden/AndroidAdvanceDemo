package com.xxm.toolbase.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by xxm on 2017/5/14.
 * 常用工具类
 */
public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("should be initialized in application");
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

    /**
     * get deviceid
     *
     * @param context add <uses-permission android:name="READ_PHONE_STATE" />
     * @return
     */
    @SuppressLint("MissingPermission")
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

    public static boolean checkPermissions(Context context, String permission) {
        PackageManager localPackageManager = context.getPackageManager();
        return localPackageManager.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("WrongConstant")
    public static boolean checkPhoneState(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.checkPermission("android.permission.READ_PHONE_STATE", context.getPackageName()) == 0;
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
}