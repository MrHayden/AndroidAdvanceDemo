package com.xxm.toolbase.bus;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;

import com.xxm.toolbase.entity.LiveDataBusBean;
import com.xxm.toolbase.utils.AppManager;

/**
 * Created by xxm on 2019/2/28 0028
 * livedatdbus事情发送和接收方法工具类
 */
public class LiveDataBusUtil {

    private static LiveDataBusUtil util;

    public static LiveDataBusUtil getInstance() {
        if (util == null)
            util = new LiveDataBusUtil();
        return util;
    }

    /**
     * 主线程中发送数据
     *
     * @param tarKey
     * @param data
     */
    public void setLiveDataBusBeanValue(String tarKey, Object data) {
        LiveDataBus.get().with(tarKey).setValue(new LiveDataBusBean(tarKey, data));
    }

    public void setLiveDataBusBeanValue(String tarKey, Object[] data) {
        LiveDataBus.get().with(tarKey).setValue(new LiveDataBusBean(tarKey, data));
    }

    public void setValue(String tarKey, Object data) {
        LiveDataBus.get().with(tarKey).setValue(data);
    }

    /**
     * 子线程中发送数据
     *
     * @param tarKey
     * @param data
     */
    public void postLiveDataBusBeanValue(String tarKey, Object data) {
        LiveDataBus.get().with(tarKey).postValue(new LiveDataBusBean(tarKey, data));
    }

    public void postLiveDataBusBeanValue(String tarKey, Object[] data) {
        LiveDataBus.get().with(tarKey).postValue(new LiveDataBusBean(tarKey, data));
    }

    public void postValue(String tarKey, Object data) {
        LiveDataBus.get().with(tarKey).postValue(data);
    }

    /**
     * 订阅事件
     *
     * @param tarKey
     * @param lifecycleOwner
     * @param observer
     */

    public <T> void observerBus(String tarKey, Class<T> mClass, LifecycleOwner lifecycleOwner, Observer<T> observer) {
        LiveDataBus.get().with(tarKey, mClass)
                .observe(lifecycleOwner, observer);
    }

    public <T> void observerBus(String tarKey, Class<T> mClass, Observer<T> observer) {
        Activity activity = AppManager.currentActivity();
        if (activity == null) return;
        LiveDataBus.get().with(tarKey, mClass)
                .observe((LifecycleOwner) AppManager.currentActivity(), observer);
    }
}
