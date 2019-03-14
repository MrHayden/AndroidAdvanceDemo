package com.xxm.demoglide.proxy;

import android.util.Log;
import android.widget.ImageView;

import com.xxm.demoglide.bean.BeanGlideImg;

/**
 * Created by xxm on 2019/3/14 0014
 * 图片加载代理类
 */
public class ImageLoadProxyUtil {

    private static ImageLoadProxyUtil instance;
    private IImageLoad iImageLoad;

    private ImageLoadProxyUtil() {

    }

    public static ImageLoadProxyUtil getInstance() {
        if (instance == null) {
            instance = new ImageLoadProxyUtil();
        }
        return instance;
    }

    public void init(IImageLoad iImageLoad) {
        this.iImageLoad = iImageLoad;
    }

    public void loadImage(String url, ImageView imageView) {
        loadImage(url, imageView, null);
    }

    public void loadImage(String url, ImageView imageView, BeanGlideImg beanGlideImg) {
        loadImage(null, url, imageView, beanGlideImg);
    }

    public void loadImage(Object object, String url, ImageView imageView, BeanGlideImg beanGlideImg) {
        if (iImageLoad == null) {
            Log.e(getClass().getSimpleName(), "请调用init()方法,初始化图片加载框架.");
            return;
        }
        iImageLoad.loadImg(object, url, imageView, beanGlideImg);
    }

}
