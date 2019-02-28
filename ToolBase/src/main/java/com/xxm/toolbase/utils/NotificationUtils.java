package com.xxm.toolbase.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.xxm.toolbase.R;

/**
 * Created by 86794 on 2018/3/23.
 */

public class NotificationUtils extends ContextWrapper {

    private NotificationManager manager;
    private Notification.Builder builder;
    private NotificationCompat.Builder builderCompat;
    private Notification notification;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";

    public NotificationUtils(Context context) {
        super(context);
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            getManager().createNotificationChannel(channel);
        }
    }

    public NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public NotificationCompat.Builder getBuilderCompat() {
        if (builderCompat == null) {
            builderCompat = new NotificationCompat.Builder(getApplicationContext());
        }
        return builderCompat;
    }

    public Notification.Builder getBuilder() {
        if (builder == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel();
                builder = new Notification.Builder(getApplicationContext(), id);
            }
        }
        return builder;
    }

    public void setContentTitle(String title) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getBuilder().setContentTitle(title);
        }else{
            getBuilderCompat().setContentTitle(title);
        }
    }

    public void setContentText(CharSequence content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getBuilder().setContentText(content);
        }else{
            getBuilderCompat().setContentText(content);
        }
    }


    public void setSmallIcon(int smallIcon) {
        if (smallIcon == 0) {
            smallIcon = R.mipmap.ic_launcher;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getBuilder().setSmallIcon(smallIcon);
        }else{
            getBuilderCompat().setSmallIcon(smallIcon);
        }
    }

    /**
     * Intent notificationIntent = new Intent(MyApplication.getContext(), HomeActivity.class);
     notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
     PendingIntent intent = PendingIntent.getActivity(MyApplication.getContext(), 0,notificationIntent, 0);
     */
    public void setContentIntent(PendingIntent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getBuilder().setContentIntent(intent);
        }else{
            getBuilderCompat().setContentIntent(intent);
        }
    }

    /**
     * 将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失
     *
     * @param autoCancel
     */
    public void setAutoCancel(boolean autoCancel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getBuilder().setAutoCancel(autoCancel);
        }else{
            getBuilderCompat().setAutoCancel(autoCancel);
        }
    }

    /**
     * 将Ongoing设为true 那么notification将不能滑动删除
     *
     * @param ongoing
     */
    public void setOngoing(boolean ongoing) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getBuilder().setOngoing(ongoing);
        }else{
            getBuilderCompat().setOngoing(ongoing);
        }
    }

    /**
     * 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
     */
    public void setWhen(long when) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getBuilder().setWhen(when);
        }else{
            getBuilderCompat().setWhen(when);
        }
    }

    /**
     * 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
     * @param defaults
     */
    public void setDefaults(int defaults){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getBuilder().setDefaults(defaults);
        }else{
            getBuilderCompat().setDefaults(defaults);
        }
    }

    /**
     * 消息数量
     * @param number
     */
    public void setNumber(int number){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getBuilder().setNumber(number);
        }else{
            getBuilderCompat().setNumber(number);
        }
    }


    /**
     * 通知首次出现在通知栏，带上升动画效果的
     * @param tickerText
     */
    public void setTicker(CharSequence tickerText){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getBuilder().setTicker(tickerText);
        }else{
            getBuilderCompat().setTicker(tickerText);
        }
    }


    public void setProgress(int max, int progress, boolean indeterminate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getBuilder().setProgress(max, progress, indeterminate);
        }else{
            getBuilderCompat().setProgress(max, progress, indeterminate);
        }
    }

    public Notification getNotification() {
        if (notification == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notification = getBuilder().build();
            } else {
                notification = getBuilderCompat().build();
            }
        }
        return notification;
    }

    public void setFlags(int flags) {
        Notification notification = getNotification();
        notification.flags |= flags;
    }

    public void showNotification(int id) {
        getManager().notify(id, getNotification());
    }

}
