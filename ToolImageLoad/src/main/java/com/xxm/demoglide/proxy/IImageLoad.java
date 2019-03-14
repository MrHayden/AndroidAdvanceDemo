package com.xxm.demoglide.proxy;

import android.widget.ImageView;

import com.xxm.demoglide.bean.BeanGlideImg;

/**
 * Created by xxm on 2019/3/14 0014
 * 图片加载公共接口
 */
public interface IImageLoad {

    /**
     * 图片加载的公共方法
     *
     * @param object       传fragment，activity，context等
     * @param url          图片Url
     * @param imageView
     * @param beanGlideImg 图片附加属性
     */
    void loadImg(Object object, String url, ImageView imageView, BeanGlideImg beanGlideImg);

    void loadImg(Object object, int resId, ImageView imageView, BeanGlideImg beanGlideImg);

}
