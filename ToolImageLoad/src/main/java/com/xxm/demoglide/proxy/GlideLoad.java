package com.xxm.demoglide.proxy;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.xxm.demoglide.bean.BeanGlideImg;
import com.xxm.demoglide.glidetransform.GlideCenterCropTransform;
import com.xxm.demoglide.glidetransform.GlideFitCenterTransform;
import com.xxm.demoglide.glidetransform.GlideRoundConnerTransform;
import com.xxm.demoglide.glidetransform.GlideRoundTransform;

/**
 * Created by xxm on 2019/3/13 0013
 * glide框架加载方法
 */
public class GlideLoad implements IImageLoad {


    public GlideLoad() {
    }

    public void showImage(Fragment fragment, String url, ImageView imageView, BeanGlideImg beanGlideImg) {
        getReqBuilder(fragment, url, imageView).apply(getRequestOptions(beanGlideImg)).into(imageView);
    }

    public void showImage(String url, ImageView imageView, BeanGlideImg beanGlideImg) {
        getReqBuilder(null, url, imageView).apply(getRequestOptions(beanGlideImg)).into(imageView);
    }

    public void showImage(Fragment fragment, String url, ImageView imageView) {
        showImage(fragment, url, imageView, null);
    }

    public void showImage(String url, ImageView imageView) {
        showImage(url, imageView, null);
    }

    public RequestBuilder<Drawable> getReqBuilder(String url, ImageView imageView) {
        return getReqBuilder(null, url, imageView);
    }

    public RequestBuilder<Drawable> getReqBuilder(Fragment fragment, String url, ImageView imageView) {
        RequestBuilder<Drawable> reqBuilder = null;
        if (fragment != null) {
            reqBuilder = Glide.with(fragment).load(url);
        } else {
            Context context = imageView.getContext();
            if (context instanceof Activity)
                reqBuilder = Glide.with((Activity) context).load(url);
        }
        //交叉淡入变换
        return reqBuilder.transition(DrawableTransitionOptions.withCrossFade());
    }

    public RequestOptions getRequestOptions(ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        ScaleType scaleType = imageView.getScaleType();
        if (scaleType == ScaleType.CENTER_CROP) {
            requestOptions.centerCrop();
        } else {
            requestOptions.fitCenter();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && imageView.getAdjustViewBounds()) {
            requestOptions.override(Target.SIZE_ORIGINAL);
        }
        return requestOptions;
    }

    public RequestOptions getRequestOptions(BeanGlideImg glideImgInfo) {
        RequestOptions requestOptions = new RequestOptions();
        if (glideImgInfo.isRound())
            requestOptions.transform(new GlideRoundTransform(glideImgInfo.getBorderWidth(), glideImgInfo.getBorderColor()));
        else if (glideImgInfo.getRoundingRadius() != 0)
            requestOptions.transform(new GlideRoundConnerTransform(glideImgInfo.getRoundingRadius(), glideImgInfo.getBorderWidth(), glideImgInfo.getBorderColor()));
        else if (glideImgInfo.getScaleType() == ImageView.ScaleType.FIT_CENTER)
            requestOptions.transform(new GlideFitCenterTransform(glideImgInfo.getBorderWidth(), glideImgInfo.getBorderColor()));
        else
            requestOptions.transform(new GlideCenterCropTransform(glideImgInfo.getBorderWidth(), glideImgInfo.getBorderColor()));
        if (glideImgInfo.getPlaceHolderRes() != 0)
            requestOptions.placeholder(glideImgInfo.getPlaceHolderRes());
        else if (glideImgInfo.getPlaceHolder() != null)
            requestOptions.placeholder(glideImgInfo.getPlaceHolder());
        if (glideImgInfo.getErrorImgRes() != 0)
            requestOptions.placeholder(glideImgInfo.getErrorImgRes());
        else if (glideImgInfo.getErrorImg() != null)
            requestOptions.placeholder(glideImgInfo.getErrorImg());
        if (glideImgInfo.isAdjustBounds()) {
            requestOptions.override(Target.SIZE_ORIGINAL);
        }
        return requestOptions;
    }

    @Override
    public void loadImg(Object object, String url, ImageView imageView, BeanGlideImg beanGlideImg) {
        if (object != null && object instanceof Fragment) {
            showImage((Fragment) object, url, imageView, beanGlideImg);
        } else {
            showImage(url, imageView, beanGlideImg);
        }

    }
}
