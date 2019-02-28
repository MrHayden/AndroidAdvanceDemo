package com.xxm.toolbase.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.xxm.toolbase.R;

/**
 * Created by shuaq on 2016/8/20.
 */

public class WgScalemageView extends AppCompatImageView {

    private double mScale = 0;

    protected Context mContext;

    public WgScalemageView(Context context) {
        super(context);
        mContext = context;
        initThis(null);
    }

    public WgScalemageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initThis(attrs);
    }

    public WgScalemageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initThis(attrs);
    }

    public void setScale(double scale) {
        mScale = scale;
        invalidate();
    }

    protected void initThis(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs,
                R.styleable.WgScalemageView);
        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int itemId = ta.getIndex(i);
            if (itemId == R.styleable.WgScalemageView_wgScale) {
                mScale = ta.getFloat(itemId, 0);
            }
        }
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mScale > 0) {
            int width = measureSize(widthMeasureSpec);
            int height = (int) (width * mScale);
            setMeasuredDimension(width, height);
        } else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measureSize(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 500;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }
}
