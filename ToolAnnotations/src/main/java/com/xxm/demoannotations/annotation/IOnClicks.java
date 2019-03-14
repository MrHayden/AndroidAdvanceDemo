package com.xxm.demoannotations.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xxm on 2019/3/12 0012
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@IEvents(listener = View.OnClickListener.class,setOnClickListener = "setOnClickListener",methodName = "onClick")
public @interface IOnClicks {

    int[] value() default View.NO_ID;

}
