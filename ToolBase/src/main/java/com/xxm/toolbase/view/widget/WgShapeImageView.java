package com.xxm.toolbase.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xxm.toolbase.R;
import com.xxm.toolbase.entity.BeanGlideImg;
import com.xxm.toolbase.logic.LogicImgShow;

/**
 * Created by shuaq on 2016/8/20.
 */

public class WgShapeImageView extends WgScalemageView {

    private int borderWidth;
    protected int borderColor;
    private int radius;
    private int shapeType;
    private int placeHolder;
    private int errorImg;

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
                    placeHolder = ta.getResourceId(itemId, R.mipmap.ic_launcher);
                } else if (itemId == R.styleable.WgShapeImageView_wgErrorImg) {
                    errorImg = ta.getResourceId(itemId, R.mipmap.ic_launcher);
                }
            }
            ta.recycle();
        }
        mGlideImageInfo = new BeanGlideImg();
    }

    public void setUrl(String url) {
        mGlideImageInfo.setBorderWidth(borderWidth)
                .setBorderColor(borderColor)
                .setScaleType(getScaleType() == ImageView.ScaleType.CENTER_CROP ? ImageView.ScaleType.CENTER_CROP : ImageView.ScaleType.FIT_CENTER)
                .setRoundingRadius(radius)
                .setRound(shapeType != 0)
                .setPlaceHolderRes(placeHolder)
                .setErrorImgRes(errorImg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            mGlideImageInfo.setAdjustBounds(getAdjustViewBounds());
        LogicImgShow.getInstance(mContext).showImage(url, this, mGlideImageInfo);
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        if (resId == 0) {
            super.setImageResource(placeHolder != 0 ? placeHolder : resId);
            return;
        }
        mGlideImageInfo.setBorderWidth(borderWidth)
                .setBorderColor(borderColor)
                .setScaleType(getScaleType() == ImageView.ScaleType.CENTER_CROP ? ImageView.ScaleType.CENTER_CROP : ImageView.ScaleType.FIT_CENTER)
                .setRoundingRadius(radius)
                .setRound(shapeType != 0)
                .setPlaceHolderRes(placeHolder)
                .setErrorImgRes(errorImg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            mGlideImageInfo.setAdjustBounds(getAdjustViewBounds());
        LogicImgShow.getInstance(mContext).showImage(resId, this, mGlideImageInfo);
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

    public void setPlaceHolder(int placeHolder) {
        this.placeHolder = placeHolder;
    }

    public void setErrorImg(int errorImg) {
        this.errorImg = errorImg;
    }
}
