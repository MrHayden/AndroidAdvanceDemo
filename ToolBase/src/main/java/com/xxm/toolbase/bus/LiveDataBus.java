package com.xxm.toolbase.bus;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/2/27 0027
 * livedata hook源码,解决未创建订阅者也能监听到数据的问题
 */
public class LiveDataBus {

    private Map<Object, MyMutableLiveData<Object>> bus;

    public LiveDataBus() {
        bus = new HashMap<>();
    }

    private static class SingleHolder {
        private static final LiveDataBus LIVE_DATA_BUS = new LiveDataBus();
    }

    public static LiveDataBus get() {
        return SingleHolder.LIVE_DATA_BUS;
    }

    public <T> MyMutableLiveData<T> with(String tarKey, Class<T> mClass) {
        if (!bus.containsKey(tarKey)) {
            bus.put(tarKey, new MyMutableLiveData<Object>());
        }
        return (MyMutableLiveData<T>) bus.get(tarKey);
    }

    public MyMutableLiveData<Object> with(String tarKey) {
        return with(tarKey, Object.class);
    }

    public class MyMutableLiveData<T> extends MutableLiveData<T> {

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
            super.observe(owner, observer);

            try {
                hookLastVersion(observer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * hook mLaseVersion的值,使未创建订阅者时不执行onChange()方法
         *
         * @throws Exception
         */
        private <T> void hookLastVersion(@NonNull Observer<T> observer) throws Exception {
            Class<LiveData> liveDataClass = LiveData.class;
            Field fieldObservers = liveDataClass.getDeclaredField("mObservers");
            if (fieldObservers != null) {
                fieldObservers.setAccessible(true);
                Object objectObservers = fieldObservers.get(this);

                Class<?> classObservers = objectObservers.getClass();
                Method methodGet = classObservers.getDeclaredMethod("get", Object.class);
                if (methodGet != null) {
                    methodGet.setAccessible(true);
                    Object objectObserverWrapper = methodGet.invoke(objectObservers, observer);
                    Object observerWrapperValue;
                    if (objectObserverWrapper instanceof Map.Entry) {
                        observerWrapperValue = ((Map.Entry) objectObserverWrapper).getValue();
                        Class<?> superClass = observerWrapperValue.getClass().getSuperclass();
                        Field mLastVersionField = superClass.getDeclaredField("mLastVersion");
                        mLastVersionField.setAccessible(true);

                        Field mVersion = liveDataClass.getDeclaredField("mVersion");
                        mVersion.setAccessible(true);
                        Object mVersionValue = mVersion.get(this);

                        mLastVersionField.set(observerWrapperValue, mVersionValue);
                    }
                }
            }
        }
    }


}
