package com.xxm.demoglide.proxy;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

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

    @Override
    public void loadImg(Object object, String url, ImageView imageView, BeanGlideImg beanGlideImg) {
        showImage(object, url, imageView, beanGlideImg);
    }

    @Override
    public void loadImg(Object object, int resId, ImageView imageView, BeanGlideImg beanGlideImg) {
        showImage(object, resId, imageView, beanGlideImg);
    }

    private void showImage(Object object, String url, ImageView imageView, BeanGlideImg beanGlideImg) {
        RequestBuilder<Drawable> requestBuilder;
        requestBuilder = getReqBuilder(object, url, imageView);
        if (beanGlideImg != null) {
            requestBuilder.apply(getRequestOptions(beanGlideImg));
        }
        requestBuilder.into(imageView);
    }

    private void showImage(Object object, int resId, ImageView imageView, BeanGlideImg beanGlideImg) {
        RequestBuilder<Drawable> requestBuilder;
        requestBuilder = getReqBuilder(object, resId, imageView);
        if (beanGlideImg != null) {
            requestBuilder.apply(getRequestOptions(beanGlideImg));
        }
        requestBuilder.into(imageView);
    }

    private RequestBuilder<Drawable> getReqBuilder(String url, ImageView imageView) {
        return getReqBuilder(null, url, imageView);
    }

    private RequestBuilder<Drawable> getReqBuilder(Object object, String url, ImageView imageView) {
        RequestBuilder<Drawable> reqBuilder = null;
        if (object != null && object instanceof Fragment) {
            reqBuilder = Glide.with((Fragment) object).load(url);
        } else {
            Context context = imageView.getContext();
            if (context instanceof Activity)
                reqBuilder = Glide.with((Activity) context).load(url);
        }
        //交叉淡入变换
        return reqBuilder.transition(DrawableTransitionOptions.withCrossFade());
    }

    private RequestBuilder<Drawable> getReqBuilder(int resId, ImageView imageView) {
        return getReqBuilder(null, resId, imageView);
    }

    private RequestBuilder<Drawable> getReqBuilder(Object object, int resId, ImageView imageView) {
        RequestBuilder<Drawable> reqBuilder = null;
        if (object != null && object instanceof Fragment) {
            reqBuilder = Glide.with((Fragment) object).load(resId);
        } else {
            Context context = imageView.getContext();
            if (context instanceof Activity)
                reqBuilder = Glide.with((Activity) context).load(resId);
        }
        //交叉淡入变换
        return reqBuilder.transition(DrawableTransitionOptions.withCrossFade());
    }

    private RequestOptions getRequestOptions(BeanGlideImg glideImgInfo) {
        RequestOptions requestOptions = new RequestOptions();

        if (glideImgInfo == null) return requestOptions;

        if (glideImgInfo.getPlaceHolderRes() != 0)
            requestOptions.placeholder(glideImgInfo.getPlaceHolderRes());
        else if (glideImgInfo.getPlaceHolder() != null)
            requestOptions.placeholder(glideImgInfo.getPlaceHolder());

        if (glideImgInfo.getErrorImgRes() != 0)
            requestOptions.error(glideImgInfo.getErrorImgRes());
        else if (glideImgInfo.getErrorImg() != null)
            requestOptions.error(glideImgInfo.getErrorImg());

        if (glideImgInfo.isAdjustBounds())
            requestOptions.override(Target.SIZE_ORIGINAL);

        if (glideImgInfo.isRound())
            requestOptions.transform(new GlideRoundTransform(glideImgInfo.getBorderWidth(), glideImgInfo.getBorderColor()));
        else if (glideImgInfo.getRoundingRadius() != 0)
            requestOptions.transform(new GlideRoundConnerTransform(glideImgInfo.getRoundingRadius(), glideImgInfo.getBorderWidth(), glideImgInfo.getBorderColor()));
        else if (glideImgInfo.getScaleType() == ImageView.ScaleType.FIT_CENTER)
            requestOptions.transform(new GlideFitCenterTransform(glideImgInfo.getBorderWidth(), glideImgInfo.getBorderColor()));
        else
            requestOptions.transform(new GlideCenterCropTransform(glideImgInfo.getBorderWidth(), glideImgInfo.getBorderColor()));

        return requestOptions;
    }

}
