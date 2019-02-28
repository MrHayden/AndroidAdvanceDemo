package com.xxm.toolbase.entity;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by shuaqq on 2017/8/11.
 */

public class BeanGlideImg {

    private boolean isRound = false;

    private int borderWidth = 0;
    private int borderColor = 0x00000000; // 边框颜色

    private int roundingRadius = 0;

    private ImageView.ScaleType scaleType;

    private int placeHolderRes = 0;
    private Drawable placeHolder;

    private int errorImgRes = 0;
    private Drawable errorImg;

    private boolean isAdjustBounds = false;

    public boolean isRound() {
        return isRound;
    }

    public BeanGlideImg setRound(boolean round) {
        isRound = round;
        return this;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public BeanGlideImg setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public BeanGlideImg setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public int getRoundingRadius() {
        return roundingRadius;
    }

    public BeanGlideImg setRoundingRadius(int roundingRadius) {
        this.roundingRadius = roundingRadius;
        return this;
    }

    public int getPlaceHolderRes() {
        return placeHolderRes;
    }

    public BeanGlideImg setPlaceHolderRes(int placeHolderRes) {
        this.placeHolderRes = placeHolderRes;
        return this;
    }

    public Drawable getPlaceHolder() {
        return placeHolder;
    }

    public BeanGlideImg setPlaceHolder(Drawable placeHolder) {
        this.placeHolder = placeHolder;
        return this;
    }

    public int getErrorImgRes() {
        return errorImgRes;
    }

    public BeanGlideImg setErrorImgRes(int errorImgRes) {
        this.errorImgRes = errorImgRes;
        return this;
    }

    public Drawable getErrorImg() {
        return errorImg;
    }

    public BeanGlideImg setErrorImg(Drawable errorImg) {
        this.errorImg = errorImg;
        return this;
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public BeanGlideImg setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BeanGlideImg
                && (((BeanGlideImg) obj).getBorderWidth() == borderWidth)
                && (((BeanGlideImg) obj).getBorderColor() == borderColor)
                && (((BeanGlideImg) obj).getScaleType() == scaleType)
                && (((BeanGlideImg) obj).getRoundingRadius() == roundingRadius)
                && (((BeanGlideImg) obj).isRound() == isRound)
                && (((BeanGlideImg) obj).isAdjustBounds() == isAdjustBounds);
    }

    public boolean isAdjustBounds() {
        return isAdjustBounds;
    }

    public void setAdjustBounds(boolean adjustBounds) {
        isAdjustBounds = adjustBounds;
    }
}