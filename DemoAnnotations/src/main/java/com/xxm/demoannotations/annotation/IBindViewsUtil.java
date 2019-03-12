package com.xxm.demoannotations.annotation;

import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by xxm on 2019/3/12 0012
 */
public class IBindViewsUtil {

    /**
     * 反射获取注解字段内容
     *
     * @param cls 一般activity或者fragment
     */
    public static void reflectIBindViews(Object cls) {
        if (cls == null) return;
        Class<?> activityClass = cls.getClass();
        Field[] fields = activityClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(IBindViews.class)) {
                //解析IBindViews，获取控件ID。
                IBindViews iBindViews = field.getAnnotation(IBindViews.class);
                int value = iBindViews.value();
                if (value == View.NO_ID) {
                    Log.e(cls.getClass().getSimpleName(), "没有找到指定ID");
                    return;
                }
                try {
                    //反射获取findviewbyid方法
                    Method findViewById = activityClass.getMethod("findViewById", int.class);
                    //获取操作权限
                    findViewById.setAccessible(true);
                    //执行findviewbyid方法
                    Object view = findViewById.invoke(cls, value);
                    field.setAccessible(true);
                    //赋值
                    field.set(cls, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 反射设置点击事件
     *
     * @param cls
     */
    public static void reflectIOnClicks(Object cls) {
        if (cls == null) return;
        Class<?> mClass = cls.getClass();
        Method[] methods = mClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(IOnClicks.class)) {
                IOnClicks iOnClicks = method.getAnnotation(IOnClicks.class);
                int[] resIds = iOnClicks.value();
                IEvents iEvents = iOnClicks.annotationType().getAnnotation(IEvents.class);

                Class onClickListener = iEvents.listener();
                String listenerName = iEvents.setOnClickListener();
                String methodName = iEvents.methodName();

                //动态代理  cls是被代理类，cls实现了OnClickListener接口
                ProxyOnClickInvocationHandler invocationHandler = new ProxyOnClickInvocationHandler(cls);
                try {
                    Object proxy = Proxy.newProxyInstance(cls.getClass().getClassLoader(), new Class[]{onClickListener}, invocationHandler);

                    for (int id : resIds) {
                        Method findViewById = mClass.getMethod("findViewById", int.class);
                        findViewById.setAccessible(true);
                        //获得view
                        Object view = findViewById.invoke(cls, id);

                        //获得setOnClickListener方法
                        Method setOnClickListener = view.getClass().getMethod(listenerName,onClickListener);
                        setOnClickListener.setAccessible(true);
                        setOnClickListener.invoke(view, proxy);

                        //设置view的点击事件
//                        ((View) view).setOnClickListener((View.OnClickListener) cls);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
