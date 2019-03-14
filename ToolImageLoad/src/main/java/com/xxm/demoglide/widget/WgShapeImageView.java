package com.xxm.demoglide.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;

import com.xxm.demoglide.R;
import com.xxm.demoglide.bean.BeanGlideImg;
import com.xxm.demoglide.proxy.ImageLoadProxyUtil;

/**
 * Created by xxm on 2019/3/13.
 */

public class WgShapeImageView extends WgScalemageView {

    private int borderWidth;
    protected int borderColor;
    private int radius;
    private int shapeType;
    private int placeHolderResId;
    private int errorImgResId;

    private BeanGlideImg mGlideImageInfo;

    public WgShapeImageView(Context context) {
        super(context);
    }

    public WgShapeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WgShapeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initThis(AttributeSet attrs) {
        super.initThis(attrs);
        if (attrs != null) {
            TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.WgShapeImageView);
            int count = ta.getIndexCount();
            for (int i = 0; i < count; i++) {
                int itemId = ta.getIndex(i);
                if (itemId == R.styleable.WgShapeImageView_wgBorderWidth) {
                    borderWidth = ta.getDimensionPixelOffset(itemId, 0);
                } else if (itemId == R.styleable.WgShapeImageView_wgBorderColor) {
                    borderColor = ta.getColor(itemId, 0);
                } else if (itemId == R.styleable.WgShapeImageView_wgRadius) {
                    radius = ta.getDimensionPixelOffset(itemId, 0);
                } else if (itemId == R.styleable.WgShapeImageView_wgShapeType) {
                    shapeType = ta.getInteger(itemId, 0);
                } else if (itemId == R.styleable.WgShapeImageView_wgPlaceHolder) {
                    placeHolderResId = ta.getResourceId(itemId, R.mipmap.default_place_holder_img);
                } else if (itemId == R.styleable.WgShapeImageView_wgErrorImg) {
                    errorImgResId = ta.getResourceId(itemId, R.mipmap.default_error_img);
                }
            }
            ta.recycle();
        }
        mGlideImageInfo = new BeanGlideImg();
    }

    public void setUrl(String url) {
        setGlideImageInfo();
        ImageLoadProxyUtil.getInstance().loadImage(url, this, mGlideImageInfo);
    }

    public void setResId(int resId) {
        setGlideImageInfo();
        ImageLoadProxyUtil.getInstance().loadImage(resId, this, mGlideImageInfo);
    }

    private void setGlideImageInfo(){
        mGlideImageInfo.setBorderWidth(borderWidth)
                .setBorderColor(borderColor)
                .setScaleType(getScaleType() == ScaleType.CENTER_CROP ? ScaleType.CENTER_CROP : ScaleType.FIT_CENTER)
                .setRoundingRadius(radius)
                .setRound(shapeType != 0)
                .setPlaceHolderRes(placeHolderResId)
                .setErrorImgRes(errorImgResId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            mGlideImageInfo.setAdjustBounds(getAdjustViewBounds());
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setShapeType(int shapeType) {
        this.shapeType = shapeType;
    }

    public void setPlaceHolderResId(int placeHolderResId) {
        this.placeHolderResId = placeHolderResId;
    }

    public void setErrorImgResId(int errorImgResId) {
        this.errorImgResId = errorImgResId;
    }
}
