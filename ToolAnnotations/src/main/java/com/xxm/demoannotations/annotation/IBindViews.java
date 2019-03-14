package com.xxm.demoannotations.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xxm on 2019/3/12 0012
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IBindViews {
    int value() default View.NO_ID;//控件ID
}
