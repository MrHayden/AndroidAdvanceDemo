package com.xxm.toolbase.logic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.xxm.toolbase.R;
import com.xxm.toolbase.base.BaseActivity;
import com.xxm.toolbase.entity.BeanGlideImg;
import com.xxm.toolbase.utils.PictureUtil;
import com.xxm.toolbase.view.glidetransform.GlideCenterCropTransform;
import com.xxm.toolbase.view.glidetransform.GlideFitCenterTransform;
import com.xxm.toolbase.view.glidetransform.GlideRoundConnerTransform;
import com.xxm.toolbase.view.glidetransform.GlideRoundTransform;

/**
 * Created by lwxkey on 16/6/6.
 */
public class LogicImgShow {

    private static LogicImgShow logicImgShow;
    private static Context mContext;


    private LogicImgShow(Context context) {
        this.mContext = context;
    }

    public static LogicImgShow getInstance(Context context) {
        if (logicImgShow == null) {
            logicImgShow = new LogicImgShow(context.getApplicationContext());
        }
//        logicImgShow.mContext = context;
        return logicImgShow;
    }

    /**
     * 通用图像显示
     */
    private RequestBuilder<Drawable> getDefaultRequest(ImageView imageView, String url) {
        return getDefaultRequest(null, imageView, url);
    }

    private RequestBuilder<Drawable> getDefaultRequest(Fragment frameFragment, ImageView imageView, String url) {
//        if (!TextUtils.isEmpty(url) && url.startsWith("/")) {
//            url = "file://" + url;
//        }

        RequestBuilder<Drawable> builder;

        if (frameFragment != null) {
            builder = Glide.with(frameFragment).load(url);
        } else {
            BaseActivity activity = null;
            if (imageView != null) {
                Context context = imageView.getContext();
                if (context instanceof BaseActivity) {
                    activity = (BaseActivity) context;
                }
            }
            builder = Glide.with(activity != null && !activity.isDestoryed ? activity : mContext).load(url);
        }
        return builder.transition(new DrawableTransitionOptions().crossFade());
    }

    private RequestOptions getDefaultOptions(ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        if (imageView.getScaleType() == ImageView.ScaleType.CENTER_CROP) {
            requestOptions = requestOptions.centerCrop();
        } else {
            requestOptions = requestOptions.fitCenter();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && imageView.getAdjustViewBounds()) {
            requestOptions = requestOptions.override(Target.SIZE_ORIGINAL);
        }
        return requestOptions;
    }

    private RequestBuilder<Drawable> getDefaultRequest(ImageView imageView, int res) {
        return getDefaultRequest(null, imageView, res);
    }

    private RequestBuilder<Drawable> getDefaultRequest(Fragment frameFragment, ImageView imageView, int res) {
        BaseActivity activity = null;
        RequestBuilder<Drawable> builder;
        if (frameFragment != null) {
            builder = Glide.with(frameFragment).load(res).transition(new DrawableTransitionOptions().crossFade());

        } else {
            Context context = imageView.getContext();
            if (context instanceof BaseActivity) {
                activity = (BaseActivity) context;
            }
            builder = Glide.with(activity != null && !activity.isDestoryed ? activity : mContext).load(res).transition(new DrawableTransitionOptions().crossFade());
        }
        return builder;
    }

    public void showImage(String url, ImageView draweeView) {
        showImage(null, url, draweeView);
    }

    public void showImage(Fragment frameFragment, String url, ImageView draweeView) {
        if (!TextUtils.isEmpty(url)) {
            if (url.startsWith("/")) {
                url = "file://" + url;
            }
            RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(frameFragment, draweeView, url);
            drawableRequestBuilder.apply(getDefaultOptions(draweeView)).into(draweeView);
        } else {
            draweeView.setImageResource(0);
        }
    }

    public void showImage(String url, ImageView draweeView, int placeRes) {
        showImage(null, url, draweeView, placeRes);
    }

    public void showImage(Fragment frameFragment, String url, ImageView draweeView, int placeRes) {
        if (!TextUtils.isEmpty(url)) {
//            if (url.startsWith("/")) {
//                url = "file://" + url;
//            }
            RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(frameFragment, draweeView, url);
            drawableRequestBuilder.apply(getDefaultOptions(draweeView).placeholder(placeRes)).into(draweeView);
        } else {
            draweeView.setImageResource(placeRes);
        }
    }

    public void showImage(String url, ImageView draweeView, Drawable drawable) {
        showImage(null, url, draweeView, drawable);
    }

    public void showImage(Fragment baseFrameFragment, String url, ImageView draweeView, Drawable drawable) {
        if (!TextUtils.isEmpty(url)) {
//            if (url.startsWith("/")) {
//                url = "file://" + url;
//            }
            RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(baseFrameFragment, draweeView, url);
            drawableRequestBuilder.apply(getDefaultOptions(draweeView).placeholder(drawable)).into(draweeView);
        } else {
            draweeView.setImageDrawable(drawable);
        }
    }

    public void showImage(int res, ImageView draweeView) {
        showImage(null, res, draweeView);
    }

    public void showImage(Fragment baseFrameFragment, int res, ImageView draweeView) {
        RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(baseFrameFragment, draweeView, res);
        drawableRequestBuilder.apply(getDefaultOptions(draweeView)).into(draweeView);
    }

    public void showRoundImage(String url, ImageView draweeView) {
        showRoundImage(null, url, draweeView);
    }

    public void showRoundImage(Fragment frameFragment, String url, ImageView draweeView) {
        if (!TextUtils.isEmpty(url)) {
//            if (url.startsWith("/")) {
//                url = "file://" + url;
//            }
            RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(frameFragment, draweeView, url);
            drawableRequestBuilder.apply(getDefaultOptions(draweeView).circleCrop()).into(draweeView);
        } else {
            draweeView.setImageResource(0);
        }
    }

    public void showRoundImage(String url, ImageView draweeView, int placeRes) {
        showRoundImage(null, url, draweeView, placeRes);
    }

    public void showRoundImage(Fragment frameFragment, String url, ImageView draweeView, int placeRes) {
        if (!TextUtils.isEmpty(url)) {
//            if (url.startsWith("/")) {
//                url = "file://" + url;
//            }
            RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(frameFragment, draweeView, url);
            drawableRequestBuilder.apply(getDefaultOptions(draweeView).circleCrop().placeholder(placeRes)).into(draweeView);
        } else {
            draweeView.setImageResource(placeRes);
        }
    }

    public void showRoundImage(String url, ImageView draweeView, Drawable drawable) {
        showRoundImage(null, url, draweeView, drawable);
    }

    public void showRoundImage(Fragment frameFragment, String url, ImageView draweeView, Drawable drawable) {
        if (!TextUtils.isEmpty(url)) {
//            if (url.startsWith("/")) {
//                url = "file://" + url;
//            }
            RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(frameFragment, draweeView, url);
            drawableRequestBuilder.apply(getDefaultOptions(draweeView).circleCrop().placeholder(drawable)).into(draweeView);
        } else {
            draweeView.setImageResource(0);
        }
    }

    public void showRoundImage(int res, ImageView draweeView) {
        showRoundImage(null, res, draweeView);
    }

    public void showRoundImage(Fragment frameFragment, int res, ImageView draweeView) {
        RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(frameFragment, draweeView, res);
        drawableRequestBuilder.apply(getDefaultOptions(draweeView).circleCrop()).into(draweeView);
    }

    public void showRoundImage(String url, int borderWidth, int borderColor, ImageView draweeView) {
        showRoundImage(null, url, borderWidth, borderColor, draweeView);
    }

    public void showRoundImage(Fragment frameFragment, String url, int border, int borderColor, ImageView draweeView) {
        if (!TextUtils.isEmpty(url)) {
//            if (url.startsWith("/")) {
//                url = "file://" + url;
//            }
            RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(frameFragment, draweeView, url);
            drawableRequestBuilder.apply(getDefaultOptions(draweeView).circleCrop()).into(draweeView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                draweeView.setBackground(PictureUtil.tintDrawable(draweeView.getContext().getResources().getDrawable(R.drawable.shape_header_placeholder), ColorStateList.valueOf(borderColor)));
            } else {
                draweeView.setBackgroundDrawable(PictureUtil.tintDrawable(draweeView.getContext().getResources().getDrawable(R.drawable.shape_header_placeholder), ColorStateList.valueOf(borderColor)));
            }
            draweeView.setPadding(border, border, border, border);
        } else {
            draweeView.setImageResource(0);
        }
    }

    public void showRoundImage(String url, int borderWidth, int borderColor, ImageView draweeView, int placeRes) {
        showRoundImage(null, url, borderWidth, borderColor, draweeView, placeRes);
    }

    public void showRoundImage(Fragment frameFragment, String url, int border, int borderColor, ImageView draweeView, int placeRes) {
        if (!TextUtils.isEmpty(url)) {
//            if (url.startsWith("/")) {
//                url = "file://" + url;
//            }
            RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(frameFragment, draweeView, url);
            drawableRequestBuilder.apply(getDefaultOptions(draweeView).circleCrop().placeholder(placeRes)).into(draweeView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                draweeView.setBackground(PictureUtil.tintDrawable(draweeView.getContext().getResources().getDrawable(R.drawable.shape_header_placeholder), ColorStateList.valueOf(borderColor)));
            } else {
                draweeView.setBackgroundDrawable(PictureUtil.tintDrawable(draweeView.getContext().getResources().getDrawable(R.drawable.shape_header_placeholder), ColorStateList.valueOf(borderColor)));
            }
            draweeView.setPadding(border, border, border, border);
        } else {
            draweeView.setImageResource(0);
        }
    }

    public void showRoundImage(String url, int borderWidth, int borderColor, ImageView draweeView, Drawable drawable) {
        showRoundImage(null, url, borderWidth, borderColor, draweeView, drawable);
    }

    public void showRoundImage(Fragment frameFragment, String url, int border, int borderColor, ImageView draweeView, Drawable drawable) {
        if (!TextUtils.isEmpty(url)) {
//            if (url.startsWith("/")) {
//                url = "file://" + url;
//            }
            RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(frameFragment, draweeView, url);
            drawableRequestBuilder.apply(getDefaultOptions(draweeView).circleCrop().placeholder(drawable)).into(draweeView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                draweeView.setBackground(PictureUtil.tintDrawable(draweeView.getContext().getResources().getDrawable(R.drawable.shape_header_placeholder), ColorStateList.valueOf(borderColor)));
            } else {
                draweeView.setBackgroundDrawable(PictureUtil.tintDrawable(draweeView.getContext().getResources().getDrawable(R.drawable.shape_header_placeholder), ColorStateList.valueOf(borderColor)));
            }
            draweeView.setPadding(border, border, border, border);

        } else {
            draweeView.setImageResource(0);
        }
    }

    public void showRoundImage(int res, int borderWidth, int borderColor, ImageView draweeView) {
        showRoundImage(null, res, borderWidth, borderColor, draweeView);
    }

    public void showRoundImage(Fragment frameFragment, int res, int border, int borderColor, ImageView draweeView) {
        RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(frameFragment, draweeView, res);
        drawableRequestBuilder.apply(getDefaultOptions(draweeView).circleCrop()).into(draweeView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            draweeView.setBackground(PictureUtil.tintDrawable(draweeView.getContext().getResources().getDrawable(R.drawable.shape_header_placeholder), ColorStateList.valueOf(borderColor)));
        } else {
            draweeView.setBackgroundDrawable(PictureUtil.tintDrawable(draweeView.getContext().getResources().getDrawable(R.drawable.shape_header_placeholder), ColorStateList.valueOf(borderColor)));
        }
        draweeView.setPadding(border, border, border, border);
    }

    public void showRoundCornerImage(String url, int corner, ImageView draweeView) {
        showRoundCornerImage(url, corner, 0, draweeView);
    }

    public void showRoundCornerImage(int res, int corner, ImageView draweeView) {
        showRoundCornerImage(res, corner, 0, draweeView);
    }

    public void showRoundCornerImage(String url, int corner, int border, ImageView draweeView) {
        if (!TextUtils.isEmpty(url)) {
//            if (url.startsWith("/")) {
//                url = "file://" + url;
//            }
            RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(draweeView, url);
            drawableRequestBuilder.apply(getDefaultOptions(draweeView).transform(new GlideRoundConnerTransform(corner, 0, 0))).into(draweeView);
//            drawableRequestBuilder.bitmapTransform(new RoundedCornersTransformation(mContext, corner, border));
        } else {
            draweeView.setImageResource(0);
        }
    }

    public void showRoundCornerImage(int res, int corner, int border, ImageView draweeView) {
        RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(draweeView, res);
        drawableRequestBuilder.apply(getDefaultOptions(draweeView).transform(new GlideRoundConnerTransform(corner, border, 0))).into(draweeView);
//        drawableRequestBuilder.bitmapTransform(new RoundedCornersTransformation(mContext, corner, border));
    }

    public void showGif(String url, ImageView draweeView) {
//        getDefaultRequest(null, url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(draweeView);
    }

    public void showBlur(String url, ImageView draweeView) {
        if (!TextUtils.isEmpty(url)) {
//            if (url.startsWith("/")) {
//                url = "file://" + url;
//            }
//            RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(url, draweeView);
//            drawableRequestBuilder.bitmapTransform(new BlurTransformation(mContext));
//            drawableRequestBuilder.into(draweeView);
        } else {
            draweeView.setImageResource(0);
        }
    }

    public void showBlur(int res, ImageView draweeView) {
        RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(draweeView, res);
//        drawableRequestBuilder.bitmapTransform(new BlurTransformation(mContext));
        drawableRequestBuilder.into(draweeView);
    }

    public void showImage(String url, ImageView draweeView, BeanGlideImg glideImgInfo) {
        RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(draweeView, url);
        drawableRequestBuilder.apply(getDefaultOptions(glideImgInfo)).into(draweeView);
    }

    public void showImage(int res, ImageView draweeView, BeanGlideImg glideImgInfo) {
        RequestBuilder<Drawable> drawableRequestBuilder = getDefaultRequest(draweeView, res);
        drawableRequestBuilder.apply(getDefaultOptions(glideImgInfo)).into(draweeView);
    }

    private RequestOptions getDefaultOptions(BeanGlideImg glideImgInfo) {
        RequestOptions requestOptions = new RequestOptions();
        if (glideImgInfo.isRound())
            requestOptions = requestOptions.transform(new GlideRoundTransform(glideImgInfo.getBorderWidth(), glideImgInfo.getBorderColor()));
        else if (glideImgInfo.getRoundingRadius() != 0)
            requestOptions = requestOptions.transform(new GlideRoundConnerTransform(glideImgInfo.getRoundingRadius(), glideImgInfo.getBorderWidth(), glideImgInfo.getBorderColor()));
        else if (glideImgInfo.getScaleType() == ImageView.ScaleType.FIT_CENTER)
            requestOptions = requestOptions.transform(new GlideFitCenterTransform(glideImgInfo.getBorderWidth(), glideImgInfo.getBorderColor()));
        else
            requestOptions = requestOptions.transform(new GlideCenterCropTransform(glideImgInfo.getBorderWidth(), glideImgInfo.getBorderColor()));
        if (glideImgInfo.getPlaceHolderRes() != 0)
            requestOptions = requestOptions.placeholder(glideImgInfo.getPlaceHolderRes());
        else if (glideImgInfo.getPlaceHolder() != null)
            requestOptions = requestOptions.placeholder(glideImgInfo.getPlaceHolder());
        if (glideImgInfo.getErrorImgRes() != 0)
            requestOptions = requestOptions.placeholder(glideImgInfo.getErrorImgRes());
        else if (glideImgInfo.getErrorImg() != null)
            requestOptions = requestOptions.placeholder(glideImgInfo.getErrorImg());
        if (glideImgInfo.isAdjustBounds()) {
            requestOptions = requestOptions.override(Target.SIZE_ORIGINAL);
        }
        return requestOptions;
    }
}
